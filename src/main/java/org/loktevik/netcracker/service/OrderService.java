package org.loktevik.netcracker.service;

import org.loktevik.netcracker.domain.Order;

import java.util.List;

/**
 * Service interface for Order model.
 */
public interface OrderService {

    /**
     * Finds order with specified id.
     * @param id id of order.
     * @return instance of order with specified id.
     */
    Order getById(Long id);

    /**
     * Finds all orders in database.
     * @return List of orders from the database.
     */
    List<Order> getAll();

    /**
     * Finds orders for customer with specified id.
     * @param id id of customer.
     * @return List of orders for specified customer id.
     */
    List<Order> getByCustomerId(Long id);

    /**
     * Saves new or updates existing order.
     * @param order new or existing order.
     * @return saved or updated order.
     */
    Order saveOrder(Order order);

    /**
     * Sets new status for order with specified id.
     * @param orderId id of order which gets new status.
     * @param statusId id of status.
     * @return order with updated status.
     */
    Order setStatus(Long orderId, Long statusId);

    /**
     * Updates order in the database.
     * @param order instance of order.
     * @return updated order.
     */
    Order updateOrder(Order order);

    /**
     * Checks if orders' delivery dates are before or equal to current date and resets them if so.
     */
    void updateOrdersStatuses();

    /**
     * Deletes order with specified id.
     * @param id id of order.
     */
    void deleteById(Long id);
}
