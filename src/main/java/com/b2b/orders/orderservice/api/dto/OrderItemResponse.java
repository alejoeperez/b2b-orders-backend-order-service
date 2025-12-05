package com.b2b.orders.orderservice.api.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
        String productId,
        String productName,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal taxPercent,
        BigDecimal discountPercent,
        BigDecimal lineNetAmount,
        BigDecimal lineTaxAmount,
        BigDecimal lineGrossAmount
) {
}
