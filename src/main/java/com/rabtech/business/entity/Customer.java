package com.rabtech.business.entity;

public class Customer extends BusinessEntity {

    private final String name;

    public Customer(String id, String name) {
        super(id);

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getEntityType() {
        return "CUSTOMER";
    }
}