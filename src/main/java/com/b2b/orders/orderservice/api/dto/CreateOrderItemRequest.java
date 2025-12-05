package com.b2b.orders.orderservice.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateOrderItemRequest(
        @NotBlank
        String productId,

        @NotBlank
        String productName,

        @Positive
        int quantity,

        @PositiveOrZero
        BigDecimal unitPrice,

        @PositiveOrZero
        @Max(100)
        BigDecimal taxPercent,

        @PositiveOrZero
        @Max(100)
        BigDecimal discountPercent
) {
}
