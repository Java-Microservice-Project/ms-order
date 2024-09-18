package com.example.ms_order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(
        name = "ms-product",
        url = "http://localhost:8082/api/v1/products",
        configuration = CustomErrorDecoder.class
)
public interface ProductClient {
    @GetMapping("/{id}")
    ProductResponseDto getProductById(@PathVariable Long id);

    @PostMapping("reduce/{id}")
    void reduceProductCount(@PathVariable Long id, @RequestParam Integer count);
}
