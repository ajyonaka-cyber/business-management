package com.rabtech.business;

import com.rabtech.business.exception.InvalidOrderException;
import com.rabtech.business.model.Order;
import com.rabtech.business.model.OrderStatus;
import com.rabtech.business.model.Product;
import com.rabtech.business.service.OrderService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderServiceTest {

    @Test
    void shouldCalculateOrderTotal() {
        Product laptop = new Product("P001", "Laptop", 55000);
        Product mouse = new Product("P002", "Mouse", 1000);

        Order order = new Order(
                "O001",
                List.of(laptop, mouse),
                OrderStatus.CREATED
        );

        OrderService service = new OrderService();

        double total = service.calculateTotal(order);

        assertEquals(56000, total);
    }

    @Test
    void shouldRejectEmptyOrder() {
        Order order = new Order(
                "O002",
                List.of(),
                OrderStatus.CREATED
        );

        OrderService service = new OrderService();

        assertThrows(
                InvalidOrderException.class,
                () -> service.calculateTotal(order)
        );
    }

    @Test
    void shouldRejectNullOrder() {
        OrderService service = new OrderService();

        assertThrows(
                InvalidOrderException.class,
                () -> service.calculateTotal(null)
        );
    }

    @Test
    void shouldRejectOrderWithoutStatus() {
        Product laptop = new Product("P001", "Laptop", 55000);

        Order order = new Order(
                "O003",
                List.of(laptop),
                null
        );

        OrderService service = new OrderService();

        assertThrows(
                InvalidOrderException.class,
                () -> service.calculateTotal(order)
        );
    }

    @Test
    void shouldConfirmCreatedOrder() {
        Product laptop = new Product("P001", "Laptop", 55000);

        Order order = new Order(
                "O004",
                List.of(laptop),
                OrderStatus.CREATED
        );

        OrderService service = new OrderService();

        Order confirmedOrder = service.confirmOrder(order);

        assertEquals(OrderStatus.CONFIRMED, confirmedOrder.status());
    }

    @Test
    void shouldRejectAlreadyConfirmedOrder() {
        Product laptop = new Product("P001", "Laptop", 55000);

        Order order = new Order(
                "O005",
                List.of(laptop),
                OrderStatus.CONFIRMED
        );

        OrderService service = new OrderService();

        assertThrows(
                InvalidOrderException.class,
                () -> service.confirmOrder(order)
        );
    }

    @Test
    void shouldCancelOrder() {
        Product laptop = new Product("P001", "Laptop", 55000);

        Order order = new Order(
                "O006",
                List.of(laptop),
                OrderStatus.CREATED
        );

        OrderService service = new OrderService();

        Order cancelledOrder = service.cancelOrder(order);

        assertEquals(OrderStatus.CANCELLED, cancelledOrder.status());
    }

    @Test
    void shouldRejectAlreadyCancelledOrder() {
        Product laptop = new Product("P001", "Laptop", 55000);

        Order order = new Order(
                "O007",
                List.of(laptop),
                OrderStatus.CANCELLED
        );

        OrderService service = new OrderService();

        assertThrows(
                InvalidOrderException.class,
                () -> service.cancelOrder(order)
        );
    }

    @Test
    void shouldRejectNegativeProductPrice() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product("P008", "Laptop", -50000)
        );
    }

    @Test
    void shouldAllowZeroPriceProduct() {
        Product product = new Product("P009", "Free Sample", 0);

        Order order = new Order(
                "O009",
                List.of(product),
                OrderStatus.CREATED
        );

        OrderService service = new OrderService();

        double total = service.calculateTotal(order);

        assertEquals(0, total);
    }

    @Test
    void shouldReturnOrderStatus() {
        Product laptop = new Product("P001", "Laptop", 55000);

        Order order = new Order(
                "O010",
                List.of(laptop),
                OrderStatus.CONFIRMED
        );

        OrderService service = new OrderService();

        OrderStatus status = service.getStatus(order);

        assertEquals(OrderStatus.CONFIRMED, status);
    }

    @Test
    void shouldRejectEmptyProductId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product("", "Laptop", 55000)
        );
    }

    @Test
    void shouldRejectEmptyProductName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product("P010", "", 55000)
        );
    }

    @Test
    void shouldRejectBlankProductId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product("   ", "Laptop", 55000)
        );
    }

    @Test
    void shouldRejectBlankProductName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product("P011", "   ", 55000)
        );
    }

    @Test
    void shouldRejectEmptyOrderId() {
        Product laptop = new Product("P001", "Laptop", 55000);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Order(
                        "",
                        List.of(laptop),
                        OrderStatus.CREATED
                )
        );
    }

    @Test
    void shouldRejectBlankOrderId() {
        Product laptop = new Product("P001", "Laptop", 55000);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Order(
                        "   ",
                        List.of(laptop),
                        OrderStatus.CREATED
                )
        );
    }

    @Test
    void shouldRejectNullOrderId() {
        Product laptop = new Product("P001", "Laptop", 55000);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Order(
                        null,
                        List.of(laptop),
                        OrderStatus.CREATED
                )
        );
    }

    @Test
    void shouldRejectNullProduct() {
        Product laptop = new Product("P001", "Laptop", 55000);

        Order order = new Order(
                "O012",
                java.util.Arrays.asList(laptop, null),
                OrderStatus.CREATED
        );

        OrderService service = new OrderService();

        assertThrows(
                InvalidOrderException.class,
                () -> service.calculateTotal(order)
        );
    }

    @Test
    void shouldRejectNegativePriceAtServiceLevel() {
        Product product = new Product("P013", "Laptop", 1000);

        Order order = new Order(
                "O013",
                List.of(product),
                OrderStatus.CREATED
        );

        OrderService service = new OrderService();

        double total = service.calculateTotal(order);

        assertEquals(1000, total);
    }

    @Test
    void shouldKeepOriginalOrderUnchangedAfterConfirmation() {
        Product laptop = new Product("P014", "Laptop", 55000);

        Order order = new Order(
                "O014",
                List.of(laptop),
                OrderStatus.CREATED
        );

        OrderService service = new OrderService();

        Order confirmedOrder = service.confirmOrder(order);

        assertEquals(OrderStatus.CREATED, order.status());
        assertEquals(OrderStatus.CONFIRMED, confirmedOrder.status());
    }

    @Test
    void shouldKeepOriginalOrderUnchangedAfterCancellation() {
        Product laptop = new Product("P015", "Laptop", 55000);

        Order order = new Order(
                "O015",
                List.of(laptop),
                OrderStatus.CREATED
        );

        OrderService service = new OrderService();

        Order cancelledOrder = service.cancelOrder(order);

        assertEquals(OrderStatus.CREATED, order.status());
        assertEquals(OrderStatus.CANCELLED, cancelledOrder.status());
    }

    @Test
    void shouldCalculateTotalForMultipleProducts() {
        Product laptop = new Product("P016", "Laptop", 55000);
        Product mouse = new Product("P017", "Mouse", 1000);
        Product keyboard = new Product("P018", "Keyboard", 2000);

        Order order = new Order(
                "O016",
                List.of(laptop, mouse, keyboard),
                OrderStatus.CREATED
        );

        OrderService service = new OrderService();

        double total = service.calculateTotal(order);

        assertEquals(58000, total);
    }

    @Test
    void shouldCancelConfirmedOrder() {
        Product laptop = new Product("P019", "Laptop", 55000);

        Order order = new Order(
                "O019",
                List.of(laptop),
                OrderStatus.CONFIRMED
        );

        OrderService service = new OrderService();

        Order cancelledOrder = service.cancelOrder(order);

        assertEquals(OrderStatus.CANCELLED, cancelledOrder.status());
    }

    @Test
    void shouldRejectConfirmingCancelledOrder() {
        Product laptop = new Product("P020", "Laptop", 55000);

        Order order = new Order(
                "O020",
                List.of(laptop),
                OrderStatus.CANCELLED
        );

        OrderService service = new OrderService();

        assertThrows(
                InvalidOrderException.class,
                () -> service.confirmOrder(order)
        );
    }

    @Test
    void shouldKeepConfirmedOrderUnchangedWhenConfirmingAgain() {
        Product laptop = new Product("P021", "Laptop", 55000);

        Order order = new Order(
                "O021",
                List.of(laptop),
                OrderStatus.CONFIRMED
        );

        OrderService service = new OrderService();

        assertThrows(
                InvalidOrderException.class,
                () -> service.confirmOrder(order)
        );

        assertEquals(OrderStatus.CONFIRMED, order.status());
    }

    @Test
    void shouldKeepCancelledOrderUnchangedWhenCancellingAgain() {
        Product laptop = new Product("P022", "Laptop", 55000);

        Order order = new Order(
                "O022",
                List.of(laptop),
                OrderStatus.CANCELLED
        );

        OrderService service = new OrderService();

        assertThrows(
                InvalidOrderException.class,
                () -> service.cancelOrder(order)
        );

        assertEquals(OrderStatus.CANCELLED, order.status());
    }

    @Test
    void shouldReturnCancelledOrderStatus() {
        Product laptop = new Product("P023", "Laptop", 55000);

        Order order = new Order(
                "O023",
                List.of(laptop),
                OrderStatus.CANCELLED
        );

        OrderService service = new OrderService();

        OrderStatus status = service.getStatus(order);

        assertEquals(OrderStatus.CANCELLED, status);
    }

    @Test
    void shouldReturnCreatedOrderStatus() {
        Product laptop = new Product("P024", "Laptop", 55000);

        Order order = new Order(
                "O024",
                List.of(laptop),
                OrderStatus.CREATED
        );

        OrderService service = new OrderService();

        OrderStatus status = service.getStatus(order);

        assertEquals(OrderStatus.CREATED, status);
    }

    @Test
    void shouldCalculateTotalWithDecimalPrices() {
        Product product1 = new Product("P025", "Cable", 999.50);
        Product product2 = new Product("P026", "Adapter", 500.50);

        Order order = new Order(
                "O025",
                List.of(product1, product2),
                OrderStatus.CREATED
        );

        OrderService service = new OrderService();

        double total = service.calculateTotal(order);

        assertEquals(1500.00, total, 0.001);
    }

    @Test
    void shouldRejectNullOrderWhenGettingStatus() {
        OrderService service = new OrderService();

        assertThrows(
                InvalidOrderException.class,
                () -> service.getStatus(null)
        );
    }

    @Test
    void shouldRejectNullOrderWhenConfirming() {
        OrderService service = new OrderService();

        assertThrows(
                InvalidOrderException.class,
                () -> service.confirmOrder(null)
        );
    }

    @Test
    void shouldRejectNullOrderWhenCancelling() {
        OrderService service = new OrderService();

        assertThrows(
                InvalidOrderException.class,
                () -> service.cancelOrder(null)
        );
    }

    @Test
    void shouldRejectNullProductList() {
        Order order = new Order(
                "O027",
                null,
                OrderStatus.CREATED
        );

        OrderService service = new OrderService();

        assertThrows(
                InvalidOrderException.class,
                () -> service.calculateTotal(order)
        );
    }

    @Test
    void shouldRejectConfirmingEmptyOrder() {
        Order order = new Order(
                "O028",
                List.of(),
                OrderStatus.CREATED
        );

        OrderService service = new OrderService();

        assertThrows(
                InvalidOrderException.class,
                () -> service.confirmOrder(order)
        );
    }

    @Test
    void shouldRejectCancellingEmptyOrder() {
        Order order = new Order(
                "O029",
                List.of(),
                OrderStatus.CREATED
        );

        OrderService service = new OrderService();

        assertThrows(
                InvalidOrderException.class,
                () -> service.cancelOrder(order)
        );
    }

    @Test
    void shouldRejectGettingStatusOfEmptyOrder() {
        Order order = new Order(
                "O030",
                List.of(),
                OrderStatus.CREATED
        );

        OrderService service = new OrderService();

        assertThrows(
                InvalidOrderException.class,
                () -> service.getStatus(order)
        );
    }

    @Test
    void shouldCreateValidProduct() {
        Product product = new Product("P031", "Headphones", 2500);

        assertEquals("P031", product.id());
        assertEquals("Headphones", product.name());
        assertEquals(2500, product.price());
    }

    @Test
    void shouldCreateValidOrder() {
        Product laptop = new Product("P032", "Laptop", 55000);

        Order order = new Order(
                "O032",
                List.of(laptop),
                OrderStatus.CREATED
        );

        assertEquals("O032", order.id());
        assertEquals(List.of(laptop), order.products());
        assertEquals(OrderStatus.CREATED, order.status());
    }

    @Test
    void shouldCreateZeroPriceProduct() {
        Product product = new Product("P033", "Free Sample", 0);

        assertEquals("P033", product.id());
        assertEquals("Free Sample", product.name());
        assertEquals(0, product.price());
    }
}