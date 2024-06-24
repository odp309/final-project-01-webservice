package com.bni.finalproject01webservice.controller.websocket;

import com.bni.finalproject01webservice.dto.exchange_rate.response.ExchangeRateResponseDTO;
import com.bni.finalproject01webservice.interfaces.ExchangeRateInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ExchangeRateWebSocketController {

    @Autowired
    private ExchangeRateInterface exchangeRateService;

    @MessageMapping("/exchange-rate")
    @SendTo("/topic/exchange-rate")
    public List<ExchangeRateResponseDTO> getAllExchangeRate() {
        return exchangeRateService.getAllExchangeRate();
    }
}
