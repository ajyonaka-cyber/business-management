package com.rabtech.business.model;

public record Product(
        String id,
        String name,
        double price
) {

    public Product {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Product ID cannot be empty");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }

        if (price < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }
    }
}