package com.example.ms_order.mapper;

import com.example.ms_order.dao.entity.OrderEntity;
import com.example.ms_order.model.request.OrderProductDto;
import com.example.ms_order.model.response.OrderResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {
    OrderMapper ORDER_MAPPER = Mappers.getMapper(OrderMapper.class);

    OrderResponseDto buildOrderResponseDto(OrderEntity entity);

    @Mapping(target = "price", expression = "java(price)")
    OrderEntity buildOrderEntity(OrderProductDto dto, BigDecimal price);
}