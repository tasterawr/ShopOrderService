package org.loktevik.netcracker.service.impl;

import org.loktevik.netcracker.service.DateProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Implementation for DateProvider interface.
 */
@Component
public class DateProviderImpl implements DateProvider {

    /**
     * Creates delivery date for order using current date.
     * @return created delivery date.
     */
    @Override
    public LocalDateTime getDeliveryDate() {
//        return LocalDateTime.now().plusDays(3).withHour(12);
        return LocalDateTime.now().plusMinutes(2);
    }
}
