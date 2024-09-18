package com.example.ms_order.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(
        name = "ms-customer",
        url = "http://localhost:8080/api/v1/customers",
        configuration = CustomErrorDecoder.class
)
public interface CustomerClient {
    @GetMapping("/{id}")
    CustomerResponseDto getCustomerById(@PathVariable Long id);

    @PostMapping("reduce/{id}")
    void reduceBalance(@PathVariable Long id, @RequestParam BigDecimal totalAmount);
}