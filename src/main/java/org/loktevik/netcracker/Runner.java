package org.loktevik.netcracker;

import lombok.RequiredArgsConstructor;
import org.loktevik.netcracker.domain.Status;
import org.loktevik.netcracker.service.StatusService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {
    private final StatusService statusService;

    @Override
    public void run(String... args) throws Exception {
        Status status1 = new Status(null, "Ожидает оплаты");
        Status status2 = new Status(null, "Оплачен. В доставке");
        Status status3 = new Status(null, "Получен");

        statusService.saveStatus(status1);
        statusService.saveStatus(status2);
        statusService.saveStatus(status3);
    }
}
