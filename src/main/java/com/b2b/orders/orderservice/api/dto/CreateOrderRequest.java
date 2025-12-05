package com.b2b.orders.orderservice.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateOrderRequest(
        @NotBlank
        String customerId,

        String requestedDeliveryDate,

        @NotBlank
        String currency,

        @NotEmpty
        List<CreateOrderItemRequest> items
) {
}
