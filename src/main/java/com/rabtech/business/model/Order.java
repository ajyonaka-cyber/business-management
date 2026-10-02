package com.rabtech.business.model;

import java.util.List;

public record Order(
        String id,
        List<Product> products,
        OrderStatus status
) {

    public Order {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Order ID cannot be empty");
        }
    }
}