package com.rabtech.business.directory;

import com.rabtech.business.entity.BusinessEntity;

import java.util.HashMap;
import java.util.Map;

public class EntityDirectory {

    private final Map<String, BusinessEntity> entities = new HashMap<>();

    public void add(BusinessEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        entities.put(entity.getId(), entity);
    }

    public BusinessEntity findById(String id) {
        return entities.get(id);
    }

    public int size() {
        return entities.size();
    }
}