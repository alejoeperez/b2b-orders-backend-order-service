package com.b2b.orders.orderservice.infrastructure;

import com.b2b.orders.orderservice.domain.Order;
import com.b2b.orders.orderservice.domain.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is created for avoiding injection error in OrderServiceImpl about OrderRepository
 */
@Repository
public class InMemoryOrderRepository implements OrderRepository {

    //map for saving in local
    private final Map<String, Order> storage = new ConcurrentHashMap<>();


    @Override
    public Order save(Order order) {
        storage.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<Order> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Order> findByCustomerId(String customerId) {
        return storage.values().stream()
                .filter(o -> o.getCustomerId().equals(customerId))
                .toList();
    }
}
