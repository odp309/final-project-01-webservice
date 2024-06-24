package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.exchange_rate.response.ExchangeRateResponseDTO;
import com.bni.finalproject01webservice.interfaces.ExchangeRateInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeRateNotificationService {

    @Autowired
    private ExchangeRateInterface exchangeRateService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Scheduled(fixedRate = 60000)  // Every 60 seconds
    public void sendExchangeRateUpdates() {
        List<ExchangeRateResponseDTO> exchangeRates = exchangeRateService.getAllExchangeRate();
        messagingTemplate.convertAndSend("/topic/exchange-rate", exchangeRates);
    }
}
