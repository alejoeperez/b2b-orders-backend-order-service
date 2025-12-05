package com.b2b.orders.orderservice.application;

import com.b2b.orders.orderservice.api.dto.CreateOrderRequest;
import com.b2b.orders.orderservice.domain.Order;

public interface OrderService {

    Order createOrder(CreateOrderRequest request);

    Order getOrderById(String orderId);

    void cancelOrder(String orderId);
}
