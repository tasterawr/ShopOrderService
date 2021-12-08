package org.loktevik.netcracker.repository;

import org.loktevik.netcracker.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order getById(Long id);

    List<Order> getAllByCustomerId(Long id);

    List<Order> findAll();

    Order save(Order order);

    void deleteById(Long id);
}
