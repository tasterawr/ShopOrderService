package org.loktevik.netcracker.service;

import org.loktevik.netcracker.domain.Order;

import java.util.List;

public interface OrderService {
    Order getById(Long id);

    List<Order> getAll();

    List<Order> getByCustomerId(Long id);

    Order saveOrder(Order order);

    Order setStatus(Long orderId, Long statusId);

    Order updateOrder(Order order);

    void deleteById(Long id);
}
