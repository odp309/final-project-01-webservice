package com.bni.finalproject01webservice.controller.scheduler;

import com.bni.finalproject01webservice.dto.exchange_rate.response.FrankfurterResponseDTO;
import com.bni.finalproject01webservice.interfaces.ExchangeRateInterface;
import com.bni.finalproject01webservice.interfaces.UserInterface;
import com.bni.finalproject01webservice.interfaces.UserLimitInterface;
import com.bni.finalproject01webservice.interfaces.WithdrawValasInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTask {

    private final ExchangeRateInterface exchangeRateService;
    private final WithdrawValasInterface withdrawValasService;
    private final UserInterface userService;
    private final UserLimitInterface userLimitService;

    // Cron expression: Seconds, Minutes, Hours, Day of Month, Month, Day of Week

    //    @Scheduled(cron = "0 30 21 * * ?")
    @Scheduled(cron = "0 57 16 * * ?", zone = "Asia/Jakarta")
    public FrankfurterResponseDTO addExchangeRateFrankfurter() {
        return exchangeRateService.addExchangeRateFrankfurter();
    }

    @Scheduled(cron = "0 0 18 * * ?", zone = "Asia/Jakarta")
    public void withdrawValasScheduledChecker() {
        withdrawValasService.withdrawValasScheduledChecker();
    }

    @Scheduled(cron = "0 0 18 * * ?", zone = "Asia/Jakarta")
    public void userIsCooldownChecker() {
        userService.userIsCooldownChecker();
    }

    @Scheduled(cron = "0 1 0 1 * ?", zone = "Asia/Jakarta")
    public void resetUserLimit() {
        userLimitService.resetUserLimit();
    }
}