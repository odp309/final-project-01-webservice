package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.ExchangeRateDTO;
import com.bni.finalproject01webservice.interfaces.ExchangeRateInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExchangeRateService implements ExchangeRateInterface {
    @Value("${frankfurter.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public ExchangeRateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public ExchangeRateDTO getCurrency() {
        String[] currencies = {"USD", "EUR", "JPY", "GBP", "AUD", "SGD", "THB", "NZD", "MYR", "CNY"};
        Map<String, Double> rates = new HashMap<>();
        Map<String, Double> sellRates   = new HashMap<>();

        for (String currency : currencies) {
            String url = String.format("%s/latest?from=%s&to=IDR", apiUrl, currency);
            ExchangeRateDTO response = restTemplate.getForObject(url, ExchangeRateDTO.class);
            if (response != null && response.getRates() != null && response.getRates().containsKey("IDR")) {
                rates.put(currency, response.getRates().get("IDR"));
                sellRates.put(currency, (response.getRates().get("IDR")*1.02));
            }
        }

        ExchangeRateDTO rateDTO = new ExchangeRateDTO();
        rateDTO.setBase("IDR");
        rateDTO.setRates(rates);
        rateDTO.setSellRates(sellRates);
        return rateDTO;
    }

    @Override
    public ExchangeRateDTO getCurrencySpecific(String baseCurrency) {
        String url = String.format("%s/latest?from=%s&to=IDR", apiUrl, baseCurrency);
        return restTemplate.getForObject(url, ExchangeRateDTO.class);
    }
}
