package com.rabtech.business.entity;

public class Employee extends BusinessEntity {

    private final String name;

    public Employee(String id, String name) {
        super(id);

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Employee name cannot be empty");
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getEntityType() {
        return "EMPLOYEE";
    }
}