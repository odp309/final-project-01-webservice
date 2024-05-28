package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.ExchangeRateDTO;
import com.bni.finalproject01webservice.interfaces.ExchangeRateInterface;
import com.bni.finalproject01webservice.model.ExchangeRate;
import com.bni.finalproject01webservice.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExchangeRateService implements ExchangeRateInterface {
    @Value("${frankfurter.api.url}")
    private String apiUrl;
    private final ExchangeRateRepository exchangeRateRepository;
    private final RestTemplate restTemplate;

    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository, RestTemplate restTemplate) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public ExchangeRateDTO getCurrency() {
        String[] currencies = {"USD", "EUR", "JPY", "GBP", "AUD", "SGD", "THB", "NZD", "MYR", "CNY"};
        Map<String, Double> buyRates = new HashMap<>();
        Map<String, Double> sellRates = new HashMap<>();

        for (String currency : currencies) {
            String url = String.format("%s/latest?from=%s&to=IDR", apiUrl, currency);
            ExchangeRateDTO response = restTemplate.getForObject(url, ExchangeRateDTO.class);
            if (response != null && response.getRates() != null && response.getRates().containsKey("IDR")) {
                buyRates.put(currency, (response.getRates().get("IDR")));
                sellRates.put(currency, (response.getRates().get("IDR") * 1.02));

                // Simpan data ke dalam database
                ExchangeRate exchangeRate = new ExchangeRate();
                exchangeRate.setIdCurrency(getCurrencyId(currency));
                exchangeRate.setDate(LocalDateTime.now());
                exchangeRate.setBuyRates(response.getRates().get("IDR"));
                exchangeRate.setSellRates(response.getRates().get("IDR") * 1.02);

                exchangeRateRepository.save(exchangeRate);
            }
        }

        ExchangeRateDTO rateDTO = new ExchangeRateDTO();
        rateDTO.setBase("IDR");
        rateDTO.setBuyRates(buyRates);
        rateDTO.setSellRates(sellRates);
        return rateDTO;
    }


    private Integer getCurrencyId(String currency) {

        switch (currency) {
            case "USD":
                return 1;
            case "SGD":
                return 2;
            case "JPY":
                return 3;
            case "EUR":
                return 4;
            case "GBP":
                return 5;
            case "AUD":
                return 6;
            case "MYR":
                return 7;
            case "NZD":
                return 8;
            case "THB":
                return 9;
            case "CNY":
                return 10;
            default:
                return null;
        }
    }


    @Override
    public ExchangeRateDTO getCurrencySpecific(String baseCurrency) {
        String url = String.format("%s/latest?from=%s&to=IDR", apiUrl, baseCurrency);
        return restTemplate.getForObject(url, ExchangeRateDTO.class);
    }
}
