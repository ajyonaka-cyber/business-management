package com.rabtech.business.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTest {

    @Test
    void shouldCreateCustomer() {
        Customer customer = new Customer("C001", "Aj");

        assertEquals("C001", customer.getId());
        assertEquals("Aj", customer.getName());
        assertEquals("CUSTOMER", customer.getEntityType());
    }
}