package com.example.ms_order.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class SaveOrderDto {

    @NotNull
    BigDecimal price;

    @NotNull
    Integer count;

    @NotNull
    Long customerId;

    @NotNull
    Long productId;
}