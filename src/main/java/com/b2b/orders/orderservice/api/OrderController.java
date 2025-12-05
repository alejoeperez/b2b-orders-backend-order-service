package com.b2b.orders.orderservice.api;

import com.b2b.orders.orderservice.api.dto.CreateOrderRequest;
import com.b2b.orders.orderservice.api.dto.OrderResponse;
import com.b2b.orders.orderservice.application.OrderService;
import com.b2b.orders.orderservice.domain.Order;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request){

        Order order = orderService.createOrder(request);
        OrderResponse response = orderMapper.toResponse(order);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable String id){
        Order order = orderService.getOrderById(id);
        OrderResponse response = orderMapper.toResponse(order);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable String id){
        orderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }
}
