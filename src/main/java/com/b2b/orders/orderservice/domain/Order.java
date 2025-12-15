package com.b2b.orders.orderservice.domain;

import lombok.Getter;

import javax.swing.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Getter
public class Order {
    private final String id;
    private final String customerId;
    private OrderStatus status;
    private final Instant orderDate;
    private final LocalDate requestedDeliveryDate;
    private final String currency;
    private final List<OrderItem> items;

    private BigDecimal totalNetAmount;
    private BigDecimal totalTaxAmount;
    private BigDecimal totalGrossAmount;

    private Order(String id,
                  String customerId,
                  OrderStatus status,
                  Instant orderDate,
                  LocalDate requestedDeliveryDate,
                  String currency,
                  List<OrderItem> items,
                  BigDecimal totalNetAmount,
                  BigDecimal totalTaxAmount,
                  BigDecimal totalGrossAmount) {

        this.id = id;
        this.customerId = customerId;
        this.status = status;
        this.orderDate = orderDate;
        this.requestedDeliveryDate = requestedDeliveryDate;
        this.currency = currency;
        this.items = List.copyOf(items);
        this.totalNetAmount = totalNetAmount;
        this.totalTaxAmount = totalTaxAmount;
        this.totalGrossAmount = totalGrossAmount;
    }

    public static Order create(String id,
                               String customerId,
                               LocalDate requestedDeliveryDate,
                               String currency,
                               List<OrderItem> items) {

        if(id == null || id.isEmpty()){
            throw new IllegalArgumentException("id cannot be null or empty");
        }
        if(customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("customerId cannot be null or blank");
        }
        if(currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("currency cannot be null or blank");
        }
        if(items == null || items.isEmpty()) {
            throw new IllegalArgumentException("order must contain at least one item");
        }

        BigDecimal totalNetAmount = BigDecimal.ZERO;
        BigDecimal totalTaxAmount = BigDecimal.ZERO;
        BigDecimal totalGrossAmount = BigDecimal.ZERO;

        for(OrderItem item : items) {
            totalNetAmount = totalNetAmount.add(item.getLineNetAmount());
            totalTaxAmount = totalTaxAmount.add(item.getLineTaxAmount());
            totalGrossAmount = totalGrossAmount.add(item.getLineGrossAmount());
        }

        Instant orderDate = Instant.now();

        return new Order(
                id,
                customerId,
                OrderStatus.CREATED,
                orderDate,
                requestedDeliveryDate,
                currency,
                items,
                totalNetAmount,
                totalTaxAmount,
                totalGrossAmount
        );
    }

    public void cancel() {

        if(this.status == OrderStatus.CANCELLED) return; //do nothing

        if(this.status == OrderStatus.PAID || this.status == OrderStatus.SHIPPED) {
            throw new IllegalStateException("Cannot cancel an order that is PAID or  SHIPPED");
        }

        this.status = OrderStatus.CANCELLED;

    }

    public void changeStatus(OrderStatus status) {

        if(status == OrderStatus.CANCELLED) return;

        this.status = status;
    }
}
