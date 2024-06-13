package com.bni.finalproject01webservice.controller.scheduler;

import com.bni.finalproject01webservice.interfaces.UserInterface;
import com.bni.finalproject01webservice.interfaces.WithdrawValasInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTask {

    private final WithdrawValasInterface withdrawValasService;
    private final UserInterface userService;

//    @Scheduled(cron = "0 0 15 * * ?")
    @Scheduled(cron = "0 0 22 * * ?")
    public void withdrawValasScheduledChecker() {
        withdrawValasService.withdrawValasScheduledChecker();
    }

    @Scheduled(cron = "0 59 23 * * ?")
    public void userIsCooldownChecker() {
        userService.userIsCooldownChecker();
    }
}
