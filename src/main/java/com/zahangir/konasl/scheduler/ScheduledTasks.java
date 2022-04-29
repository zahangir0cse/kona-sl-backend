package com.zahangir.konasl.scheduler;

import com.zahangir.konasl.service.ItemService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class ScheduledTasks {

    private final ItemService itemService;

    public ScheduledTasks(ItemService itemService) {
        this.itemService = itemService;
    }

    @Scheduled(fixedDelay = 300000)
    public void clearUnpaidConfirmedBooking() {
        itemService.saveAll();
    }

}