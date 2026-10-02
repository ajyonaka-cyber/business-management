package com.rabtech.business;

import com.rabtech.business.entity.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeTest {

    @Test
    void shouldCreateEmployee() {
        Employee employee = new Employee("E001", "John");

        assertEquals("E001", employee.getId());
        assertEquals("John", employee.getName());
        assertEquals("EMPLOYEE", employee.getEntityType());
    }
}