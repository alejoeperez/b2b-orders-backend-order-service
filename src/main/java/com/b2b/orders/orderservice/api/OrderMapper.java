package com.b2b.orders.orderservice.api;

import com.b2b.orders.orderservice.api.dto.OrderItemResponse;
import com.b2b.orders.orderservice.api.dto.OrderResponse;
import com.b2b.orders.orderservice.domain.Order;
import com.b2b.orders.orderservice.domain.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderResponse toResponse(Order order) {

        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(this::toItemResponse)
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getStatus().name(),
                order.getOrderDate().toString(),
                order.getRequestedDeliveryDate() != null
                    ? order.getRequestedDeliveryDate().toString()
                    : null,
                order.getCurrency(),
                itemResponses,
                order.getTotalNetAmount(),
                order.getTotalTaxAmount(),
                order.getTotalGrossAmount()
        );
    }

    private OrderItemResponse toItemResponse(OrderItem item) {
        return new OrderItemResponse(
                item.getProductId(),
                item.getProductName(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getTaxPercent(),
                item.getDiscountPercent(),
                item.getLineNetAmount(),
                item.getLineTaxAmount(),
                item.getLineGrossAmount()
        );
    }
}
