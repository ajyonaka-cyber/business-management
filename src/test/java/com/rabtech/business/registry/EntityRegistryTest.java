package com.rabtech.business.registry;

import com.rabtech.business.entity.Customer;
import com.rabtech.business.exception.DuplicateEntityException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EntityRegistryTest {

    @Test
    void shouldRegisterUniqueEntities() {
        EntityRegistry registry = new EntityRegistry();

        Customer customer1 = new Customer("C001", "Aj");
        Customer customer2 = new Customer("C002", "John");

        registry.register(customer1);
        registry.register(customer2);

        assertEquals(2, registry.size());
        assertTrue(registry.contains("C001"));
        assertTrue(registry.contains("C002"));
    }

    @Test
    void shouldRejectDuplicateEntityWithCheckedException()
            throws DuplicateEntityException {

        EntityRegistry registry = new EntityRegistry();

        Customer customer1 = new Customer("C003", "Aj");
        Customer customer2 = new Customer("C003", "John");

        registry.registerUnique(customer1);

        assertThrows(
                DuplicateEntityException.class,
                () -> registry.registerUnique(customer2)
        );

        assertEquals(1, registry.size());
    }
}