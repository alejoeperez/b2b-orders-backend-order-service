package com.b2b.orders.orderservice.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class OrderItemTest {

    @Test
    void create_shouldCalculateAmountsCorrectly() {
        // given
        String productId = "P-100";
        String productName = "Laptop Pro";
        int quantity = 2;
        BigDecimal unitPrice = BigDecimal.valueOf(100);     // 100 USD
        BigDecimal taxPercent = BigDecimal.valueOf(19);     // 19%
        BigDecimal discountPercent = BigDecimal.valueOf(10); // 10%

        // when
        OrderItem item = OrderItem.create(
                productId,
                productName,
                quantity,
                unitPrice,
                taxPercent,
                discountPercent
        );

        // then
        // base = 100 * 2 = 200
        // net = 200 * (1 - 0.10) = 180
        // tax  = 180 * 0.19 = 34.2
        // gross = 180 + 34.2 = 214.2

        assertEquals(productId, item.getProductId());
        assertEquals(productName, item.getProductName());
        assertEquals(quantity, item.getQuantity());
        assertEquals(0, BigDecimal.valueOf(180).compareTo(item.getLineNetAmount()));
        assertEquals(0, BigDecimal.valueOf(34.2).compareTo(item.getLineTaxAmount()));
        assertEquals(0, BigDecimal.valueOf(214.2).compareTo(item.getLineGrossAmount()));
    }

    @Test
    void create_shouldFail_whenQuantityIsZeroOrNegative() {
        // given
        String productId = "P-100";
        String productName = "Laptop Pro";
        BigDecimal unitPrice = BigDecimal.valueOf(100);
        BigDecimal taxPercent = BigDecimal.valueOf(19);
        BigDecimal discountPercent = BigDecimal.ZERO;

        // when + then
        assertThrows(IllegalArgumentException.class, () ->
                OrderItem.create(productId, productName, 0, unitPrice, taxPercent, discountPercent)
        );

        assertThrows(IllegalArgumentException.class, () ->
                OrderItem.create(productId, productName, -1, unitPrice, taxPercent, discountPercent)
        );
    }

}
