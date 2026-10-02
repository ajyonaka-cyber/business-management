package com.rabtech.business.registry;

import com.rabtech.business.entity.BusinessEntity;
import com.rabtech.business.exception.DuplicateEntityException;

import java.util.HashSet;
import java.util.Set;

public class EntityRegistry {

    private final Set<String> entityIds = new HashSet<>();

    public void register(BusinessEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        entityIds.add(entity.getId());
    }

    public int size() {
        return entityIds.size();
    }

    public boolean contains(String id) {
        return entityIds.contains(id);
    }

    public void registerUnique(BusinessEntity entity)
            throws DuplicateEntityException {

        if (entity == null) {
            throw new DuplicateEntityException("Entity cannot be null");
        }

        if (entityIds.contains(entity.getId())) {
            throw new DuplicateEntityException(
                    "Entity with ID already exists: " + entity.getId()
            );
        }

        entityIds.add(entity.getId());
    }
}