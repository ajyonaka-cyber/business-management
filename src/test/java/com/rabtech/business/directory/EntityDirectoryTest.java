package com.rabtech.business.directory;

import com.rabtech.business.entity.Customer;
import com.rabtech.business.entity.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
class EntityDirectoryTest {

    @Test
    void shouldStoreAndFindEntitiesById() {
        EntityDirectory directory = new EntityDirectory();

        Customer customer = new Customer("C001", "Aj");
        Employee employee = new Employee("E001", "John");

        directory.add(customer);
        directory.add(employee);

        assertEquals(2, directory.size());
        assertEquals(customer, directory.findById("C001"));
        assertEquals(employee, directory.findById("E001"));
    }

    @Test
    void shouldReturnNullForUnknownId() {
        EntityDirectory directory = new EntityDirectory();

        assertEquals(null, directory.findById("UNKNOWN"));
    }

    @Test
    void shouldRejectNullEntity() {
        EntityDirectory directory = new EntityDirectory();

        assertThrows(
                IllegalArgumentException.class,
                () -> directory.add(null)
        );
    }
}