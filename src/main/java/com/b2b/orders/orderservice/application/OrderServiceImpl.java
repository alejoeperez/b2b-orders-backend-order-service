package com.b2b.orders.orderservice.application;

import com.b2b.orders.orderservice.api.dto.CreateOrderItemRequest;
import com.b2b.orders.orderservice.api.dto.CreateOrderRequest;
import com.b2b.orders.orderservice.domain.Order;
import com.b2b.orders.orderservice.domain.OrderItem;
import com.b2b.orders.orderservice.domain.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public Order createOrder(CreateOrderRequest request) {

        String orderId = UUID.randomUUID().toString();

        LocalDate requestedDeliveryDate = null;

        if(request.requestedDeliveryDate() != null && !request.requestedDeliveryDate().isBlank())
            requestedDeliveryDate = LocalDate.parse(request.requestedDeliveryDate());

        List<OrderItem> items = request.items().stream()
                .map(this::toOrderItem)
                .toList();

        Order order = Order.create(
                orderId,
                request.customerId(),
                requestedDeliveryDate,
                request.currency(),
                items
        );

        return orderRepository.save(order);
    }

    private OrderItem toOrderItem(CreateOrderItemRequest itemRequest) {
        return OrderItem.create(
                itemRequest.productId(),
                itemRequest.productName(),
                itemRequest.quantity(),
                itemRequest.unitPrice(),
                itemRequest.taxPercent(),
                itemRequest.discountPercent()
        );
    }

    @Override
    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow( () -> new IllegalArgumentException("Order not found: " + orderId));
    }

    @Override
    public void cancelOrder(String orderId) {
        Order order = getOrderById(orderId);
        order.cancel();
        orderRepository.save(order);
    }
}
