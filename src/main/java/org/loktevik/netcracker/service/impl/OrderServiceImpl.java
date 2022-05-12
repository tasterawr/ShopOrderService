package org.loktevik.netcracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.message.FormattedMessage;
import org.loktevik.netcracker.domain.Order;
import org.loktevik.netcracker.domain.Status;
import org.loktevik.netcracker.repository.OrderRepository;
import org.loktevik.netcracker.repository.StatusRepository;
import org.loktevik.netcracker.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service implementation class for Order model. Implements methods from OrderService.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final Logger log = Logger.getLogger(OrderServiceImpl.class);
    private final OrderRepository orderRepo;
    private final StatusRepository statusRepo;

    @Override
    public Order getById(Long id) {
        log.info(new FormattedMessage("Getting order with id {}.", id));
        Order order = orderRepo.getById(id);
        return order;
    }

    @Override
    public List<Order> getAll() {
        log.info("Getting all orders.");
        return orderRepo.findAll();
    }

    @Override
    public List<Order> getByCustomerId(Long id) {
        return orderRepo.getAllByCustomerId(id);
    }

    @Override
    public Order saveOrder(Order order) {
        log.info(new FormattedMessage("Saving new order \"{}\", customer: {}, offer: {}.",
                order.getName(), order.getCustomerId(), order.getOfferId()));
        return orderRepo.save(order);
    }

    @Override
    public Order setStatus(Long orderId, Long statusId) {
        Order order = getById(orderId);
        Status status =  statusRepo.getById(statusId);

        log.info(new FormattedMessage("Updating status for order {}, new status: {} ({}).", orderId, statusId, status.getName()));
        order.setStatus(status);
        return order;
    }

    @Override
    public Order updateOrder(Order order) {
        log.info(new FormattedMessage("Updating order with id: {}.", order.getId()));
        orderRepo.save(order);
        return order;
    }

    @Override
    public void updateOrdersStatuses() {
        LocalDateTime now = LocalDateTime.now();
        List<Order> orders = orderRepo.findAll();
        orders.forEach(order -> {
            if (order.getDeliveryTime().isBefore(now) ||
                    order.getDeliveryTime().isEqual(now)){
                order.setStatus(statusRepo.findByName("Заказ доставлен"));
                saveOrder(order);
            }
        });
    }

    @Override
    public void deleteById(Long id) {
        log.info(new FormattedMessage("Deleting order with id: {}.", id));
        orderRepo.deleteById(id);
    }
}
