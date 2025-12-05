package com.b2b.orders.orderservice.api.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(
        String id,
        String customerId,
        String status,
        String orderDate,
        String requestedDeliveryDate,
        String currency,
        List<OrderItemResponse> items,
        BigDecimal totalNetAmount,
        BigDecimal totalTaxAmount,
        BigDecimal totalGrossAmount
) {
}
