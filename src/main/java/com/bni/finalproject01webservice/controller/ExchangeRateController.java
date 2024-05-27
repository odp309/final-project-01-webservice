package com.bni.finalproject01webservice.controller;

import com.bni.finalproject01webservice.dto.ExchangeRateDTO;
import com.bni.finalproject01webservice.interfaces.ExchangeRateInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}

