package org.loktevik.netcracker.service;

import java.time.LocalDateTime;

/**
 * Interface of date provider for creating order delivery date.
 */
public interface DateProvider {

    /**
     * Creates delivery date for order.
     * @return created delivery date.
     */
    LocalDateTime getDeliveryDate();
}
