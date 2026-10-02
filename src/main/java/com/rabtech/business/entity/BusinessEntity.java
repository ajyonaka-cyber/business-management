package com.rabtech.business.entity;

public abstract class BusinessEntity {

    private final String id;

    protected BusinessEntity(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Entity ID cannot be empty");
        }

        this.id = id;
    }

    public String getId() {
        return id;
    }

    public abstract String getEntityType();
}