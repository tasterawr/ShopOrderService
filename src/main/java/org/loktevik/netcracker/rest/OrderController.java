package org.loktevik.netcracker.rest;

import lombok.RequiredArgsConstructor;
import org.loktevik.netcracker.domain.Order;
import org.loktevik.netcracker.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        Order order = orderService.getById(id);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAll();

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Order> saveOrder(@RequestBody Order order){
        orderService.saveOrder(order);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id){
        orderService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Order> updateOrder(@RequestBody Order order){
        orderService.updateOrder(order);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PatchMapping("{orderId}/status/{statusId}")
    public ResponseEntity<?> setStatusToOrder(@PathVariable Long orderId, @PathVariable Long statusId){
        orderService.setStatus(orderId, statusId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
