package com.rabtech.business.service;

import com.rabtech.business.exception.InvalidOrderException;
import com.rabtech.business.model.Order;
import com.rabtech.business.model.OrderStatus;

public class OrderService {

    public double calculateTotal(Order order) {
        validateOrder(order);

        return order.products()
                .stream()
                .mapToDouble(product -> product.price())
                .sum();
    }

    public Order confirmOrder(Order order) {
        validateOrder(order);

        if (order.status() != OrderStatus.CREATED) {
            throw new InvalidOrderException("Only CREATED orders can be confirmed");
        }

        return new Order(
                order.id(),
                order.products(),
                OrderStatus.CONFIRMED
        );
    }

    public Order cancelOrder(Order order) {
        validateOrder(order);

        if (order.status() == OrderStatus.CANCELLED) {
            throw new InvalidOrderException("Order is already cancelled");
        }

        return new Order(
                order.id(),
                order.products(),
                OrderStatus.CANCELLED
        );
    }

    private void validateOrder(Order order) {
        if (order == null) {
            throw new InvalidOrderException("Order cannot be null");
        }

        if (order.products() == null || order.products().isEmpty()) {
            throw new InvalidOrderException("Order must contain at least one product");
        }

        if (order.status() == null) {
            throw new InvalidOrderException("Order must have a status");
        }
        for (var product : order.products()) {
            if (product == null) {
                throw new InvalidOrderException("Order cannot contain a null product");
            }

            if (product.price() < 0) {
                throw new InvalidOrderException("Product price cannot be negative");
            }
        }
    }

    public OrderStatus getStatus(Order order) {
        validateOrder(order);
        return order.status();
    }
}

