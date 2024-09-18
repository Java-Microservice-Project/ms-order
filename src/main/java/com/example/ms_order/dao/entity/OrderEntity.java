package com.example.ms_order.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    BigDecimal price;
    Integer count;
    Long customerId;
    Long productId;
}