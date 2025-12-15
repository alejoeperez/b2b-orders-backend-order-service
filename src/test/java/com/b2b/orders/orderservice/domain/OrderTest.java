package com.b2b.orders.orderservice.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;

public class OrderTest {

    @Test
    void create_shouldbe_OK(){

        String id = "Order-001";
        String customerId = "Customer-001";
        LocalDate requestedDeliveryDate = LocalDate.parse("2025-12-21");
        String currency = "COP";

        // given: items reales (sin mocks)
        OrderItem item = OrderItem.create(
                "P-1", "Product", 1,
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(19),
                BigDecimal.valueOf(10)
        );

        List<OrderItem> items = List.of(item);

        Order order = Order.create("Order-001", customerId, requestedDeliveryDate, currency, items);

        //assertions
        assertEquals(id, order.getId());
        assertEquals(customerId, order.getCustomerId());
        assertEquals(requestedDeliveryDate, order.getRequestedDeliveryDate());
        assertEquals(currency, order.getCurrency());
        assertEquals(items, order.getItems());
    }

    @Test
    void create_shouldfail_currencyEmpty(){

        String id = "Order-001";
        String customerId = "Customer-001";
        LocalDate requestedDeliveryDate = LocalDate.parse("2025-12-21");
        String currency = "";

        // given: items reales (sin mocks)
        OrderItem item = OrderItem.create(
                "P-1", "Product", 1,
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(19),
                BigDecimal.valueOf(10)
        );

        List<OrderItem> items = List.of(item);

        assertThrows(IllegalArgumentException.class, () ->
                Order.create("Order-001", customerId, requestedDeliveryDate, currency, items));
    }

    @Test
    void cancel_shouldbe_OK(){

        String id = "Order-001";
        String customerId = "Customer-001";
        LocalDate requestedDeliveryDate = LocalDate.parse("2025-12-21");
        String currency = "COP";

        // given: items reales (sin mocks)
        OrderItem item = OrderItem.create(
                "P-1", "Product", 1,
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(19),
                BigDecimal.valueOf(10)
        );

        List<OrderItem> items = List.of(item);

        Order order = Order.create("Order-001", customerId, requestedDeliveryDate, currency, items);

        order.cancel();

        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    void cancel_shouldfail_statusPAID(){

        String id = "Order-001";
        String customerId = "Customer-001";
        LocalDate requestedDeliveryDate = LocalDate.parse("2025-12-21");
        String currency = "COP";

        // given: items reales (sin mocks)
        OrderItem item = OrderItem.create(
                "P-1", "Product", 1,
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(19),
                BigDecimal.valueOf(10)
        );

        List<OrderItem> items = List.of(item);

        Order order = Order.create("Order-001", customerId, requestedDeliveryDate, currency, items);

        order.changeStatus(OrderStatus.PAID);

        assertThrows(IllegalStateException.class, () -> order.cancel());
    }
}
