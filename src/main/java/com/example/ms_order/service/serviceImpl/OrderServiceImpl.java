package com.example.ms_order.service.serviceImpl;

import com.example.ms_order.exception.InsufficientConditionException;
import com.example.ms_order.model.response.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static az.atl.orderms.mapper.OrderMapper.ORDER_MAPPER;
import static az.atl.orderms.model.enums.ErrorMessage.*;
import static com.example.ms_order.mapper.OrderMapper.ORDER_MAPPER;
import static com.example.ms_order.model.enums.ErrorMessage.INSUFFICIENT_BALANCE;
import static com.example.ms_order.model.enums.ErrorMessage.INSUFFICIENT_QUANTITY;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final CustomerClient customerClient;

    @Override
    public OrderResponseDto getOrderById(Long id) {
        var entity = fetchOrderIfExist(id);
        return ORDER_MAPPER.buildOrderResponseDto(entity);
    }

    @Override
    @Transactional
    public void oderProduct(OrderProductDto dto) {
        var product = productClient.getProductById(dto.getProductId());
        var totalAmount = product.getPrice().multiply(new BigDecimal(dto.getCount()));
        var customer = customerClient.getCustomerById(dto.getCustomerId());
        var orderEntity = ORDER_MAPPER.buildOrderEntity(dto, totalAmount);

        if (product.getCount() < dto.getCount())
            throw new InsufficientConditionException(INSUFFICIENT_QUANTITY.getMessage());

        if (customer.getBalance().compareTo(totalAmount) < 0)
            throw new InsufficientConditionException(INSUFFICIENT_BALANCE.getMessage());

        productClient.reduceProductCount(dto.getProductId(), dto.getCount());
        customerClient.reduceBalance(dto.getCustomerId(), totalAmount);
        orderRepository.save(orderEntity);
    }


    private OrderEntity fetchOrderIfExist(Long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ORDER_NOT_FOUND.format(id))
        );
    }
}
