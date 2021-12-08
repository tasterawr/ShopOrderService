package org.loktevik.netcracker.rest;

import lombok.RequiredArgsConstructor;
import org.loktevik.netcracker.domain.Order;
import org.loktevik.netcracker.rest.dto.OrderInfo;
import org.loktevik.netcracker.service.DateProvider;
import org.loktevik.netcracker.service.OrderService;
import org.loktevik.netcracker.service.StatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final DateProvider dateProvider;
    private final StatusService statusService;

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        Order order = orderService.getById(id);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping(value = "customer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long id) {
        List<Order> orders = orderService.getByCustomerId(id);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAll();

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> saveOrder(@RequestBody Order order){
        orderService.saveOrder(order);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PostMapping(value="/customer/{id}/new-order", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> createCustomerOrder(@RequestBody OrderInfo info, @PathVariable Long id){
        Order order = createNewOrder(info, id);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteOrder(@PathVariable Long id){
        orderService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> updateOrder(@RequestBody Order order){
        orderService.updateOrder(order);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PatchMapping(value = "{orderId}/status/{statusId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> setStatusToOrder(@PathVariable Long orderId, @PathVariable Long statusId){
        orderService.setStatus(orderId, statusId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> sendOrder(){
        Order order1 = new Order();
        order1.setName("Заказ 1");
        order1.setCustomerId(1L);
        order1.setPaid(false);

        orderService.saveOrder(order1);

        return new ResponseEntity<>(order1, HttpStatus.OK);
    }

    public Order createNewOrder(OrderInfo info, Long customerId){
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setOfferId(info.getOfferId());
        order.setPaid(false);
        order.setAmount(info.getAmount());
        order.setDeliveryTime(dateProvider.getDeliveryDate());
        order.setStatus(statusService.getById(1L));
        orderService.saveOrder(order);
        order.setName("ЗАКАЗ №" + order.getId());
        orderService.updateOrder(order);

        return order;
    }
}
