package com.bni.finalproject01webservice.controller.public_api;

import com.bni.finalproject01webservice.dto.ExchangeRateDTO;
import com.bni.finalproject01webservice.dto.ExchangeRateResponseDTO;
import com.bni.finalproject01webservice.interfaces.ExchangeRateInterface;
import com.bni.finalproject01webservice.model.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/")
public class ExchangeRateController {

    private final ExchangeRateInterface exchangeRateService;

    @Autowired
    public ExchangeRateController(ExchangeRateInterface exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping("/getCurrencySpecific")
    public ExchangeRateDTO getCurrencySpecific(String baseCurrency) {
        return exchangeRateService.getCurrencySpecific(baseCurrency);
    }

    @GetMapping("/getCurrency")
    public ExchangeRateDTO getCurrency() {
        return exchangeRateService.getCurrency();
    }

    @GetMapping(value = "/get/exchange-rate", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ExchangeRateResponseDTO> getAllExchangeRates() {
        return exchangeRateService.getAllExchangeRates();
    }

}

