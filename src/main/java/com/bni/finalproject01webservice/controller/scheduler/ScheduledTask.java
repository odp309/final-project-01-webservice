package com.bni.finalproject01webservice.controller.scheduler;

import com.bni.finalproject01webservice.interfaces.WithdrawValasInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    @Autowired
    private WithdrawValasInterface withdrawValasService;

//    @Scheduled(cron = "0 0 15 * * ?")
    @Scheduled(cron = "0 44 11 * * ?")
    public void withdrawValasScheduledChecker() {
        withdrawValasService.withdrawValasScheduledChecker();
    }
}
