package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.ExchangeRateDTO;
import com.bni.finalproject01webservice.dto.ExchangeRateResponseDTO;
import com.bni.finalproject01webservice.interfaces.ExchangeRateInterface;
import com.bni.finalproject01webservice.model.Currency;
import com.bni.finalproject01webservice.model.ExchangeRate;
import com.bni.finalproject01webservice.repository.CurrencyRepository;
import com.bni.finalproject01webservice.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ExchangeRateService implements ExchangeRateInterface {

    @Value("${frankfurter.api.url}")
    private String apiUrl;

    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyRepository currencyRepository;
    private final RestTemplate restTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ExchangeRateDTO getCurrency() {
        String[] currencies = {"USD", "EUR", "JPY", "GBP", "AUD", "SGD", "THB", "NZD", "MYR", "CNY"};
        Map<String, BigDecimal> buyRate = new HashMap<>();
        Map<String, BigDecimal> sellRate = new HashMap<>();

        for (String currency : currencies) {
            String url = String.format("%s/latest?from=%s&to=IDR", apiUrl, currency);
            ExchangeRateDTO response = restTemplate.getForObject(url, ExchangeRateDTO.class);

            if (response != null && response.getRates() != null && response.getRates().containsKey("IDR")) {
                buyRate.put(currency, response.getRates().get("IDR").multiply(BigDecimal.valueOf(1.05)));
                sellRate.put(currency, (response.getRates().get("IDR")));

                Currency dbCurrency = currencyRepository.findByCode(currency);

                // Save data to database
                ExchangeRate exchangeRate = new ExchangeRate();
                exchangeRate.setCurrency(dbCurrency);
                exchangeRate.setCreatedAt(new Date());
                exchangeRate.setBuyRate(response.getRates().get("IDR").multiply(BigDecimal.valueOf(1.05)));
                exchangeRate.setSellRate(response.getRates().get("IDR"));

                exchangeRateRepository.save(exchangeRate);
            }
        }

        ExchangeRateDTO rateDTO = new ExchangeRateDTO();
        rateDTO.setBase("IDR");
        rateDTO.setBuyRates(buyRate);
        rateDTO.setSellRates(sellRate);
        return rateDTO;
    }

    @Override
    public ExchangeRateDTO getCurrencySpecific(String baseCurrency) {
        String url = String.format("%s/latest?from=%s&to=IDR", apiUrl, baseCurrency);
        return restTemplate.getForObject(url, ExchangeRateDTO.class);
    }

    @Override
    public List<ExchangeRateResponseDTO> getAllExchangeRates() {
        List<ExchangeRate> exchangeRates = exchangeRateRepository.findAll();

        return exchangeRates.stream()
                .map(exchangeRate -> {
                    ExchangeRateResponseDTO rateResponseDTO = new ExchangeRateResponseDTO();
                    rateResponseDTO.setCurrencyCode(exchangeRate.getCurrency().getCode());
                    rateResponseDTO.setBuyRate(exchangeRate.getBuyRate());
                    rateResponseDTO.setSellRate(exchangeRate.getSellRate());
                    rateResponseDTO.setCreatedAt(exchangeRate.getCreatedAt());
                    return rateResponseDTO;
                })
                .collect(Collectors.toList());
    }
}
