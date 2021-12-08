package org.loktevik.netcracker.service.impl;

import org.loktevik.netcracker.service.DateProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DateProviderImpl implements DateProvider {
    @Override
    public LocalDateTime getDeliveryDate() {
        return LocalDateTime.now().plusDays(3).withHour(12);
    }
}
