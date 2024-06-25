package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.exchange_rate.request.ExchangeRateRequestDTO;
import com.bni.finalproject01webservice.dto.exchange_rate.request.FrankfurterRequestDTO;
import com.bni.finalproject01webservice.dto.exchange_rate.response.ExchangeRateResponseDTO;
import com.bni.finalproject01webservice.dto.exchange_rate.response.FrankfurterResponseDTO;
import com.bni.finalproject01webservice.interfaces.DateTimeInterface;
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

    private final DateTimeInterface dateTimeService;
    private final RestTemplate restTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FrankfurterResponseDTO addExchangeRateFrankfurter() {

        List<String> currencies = currencyRepository.findAllCurrencyCode();
        Map<String, BigDecimal> buyRate = new HashMap<>();
        Map<String, BigDecimal> sellRate = new HashMap<>();

        for (String currency : currencies) {
            String url = String.format("%s/latest?from=%s&to=IDR", apiUrl, currency);
            FrankfurterRequestDTO request = restTemplate.getForObject(url, FrankfurterRequestDTO.class);

            if (request != null && request.getRates() != null && request.getRates().containsKey("IDR")) {
                buyRate.put(currency, request.getRates().get("IDR").multiply(BigDecimal.valueOf(1.02)));
                sellRate.put(currency, (request.getRates().get("IDR")));

                Currency dbCurrency = currencyRepository.findByCode(currency);

                // Save data to database
                ExchangeRate exchangeRate = new ExchangeRate();
                exchangeRate.setCurrency(dbCurrency);
                exchangeRate.setCreatedAt(dateTimeService.getCurrentDateTimeInJakarta());
                exchangeRate.setBuyRate(request.getRates().get("IDR").multiply(BigDecimal.valueOf(1.02)));
                exchangeRate.setSellRate(request.getRates().get("IDR"));

                exchangeRateRepository.save(exchangeRate);
            }
        }

        FrankfurterResponseDTO response = new FrankfurterResponseDTO();
        response.setBase("IDR");
        response.setBuyRate(buyRate);
        response.setSellRate(sellRate);
        return response;
    }

    @Override
    public ExchangeRateResponseDTO getExchangeRate(ExchangeRateRequestDTO request) {
        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRate(request.getCode());

        ExchangeRateResponseDTO response = new ExchangeRateResponseDTO();
        response.setCurrencyCode(exchangeRate.getCurrency().getCode());
        response.setCurrencyName(exchangeRate.getCurrency().getName());
        response.setCreatedAt(String.valueOf(exchangeRate.getCreatedAt()));
        response.setBuyRate(exchangeRate.getBuyRate());
        response.setSellRate(exchangeRate.getSellRate());

        return response;
    }

    @Override
    public List<ExchangeRateResponseDTO> getAllExchangeRate() {
        List<ExchangeRate> exchangeRates = exchangeRateRepository.findAllLatestExchangeRate();

        return exchangeRates.stream()
                .map(exchangeRate -> {
                    ExchangeRateResponseDTO response = new ExchangeRateResponseDTO();
                    response.setCurrencyCode(exchangeRate.getCurrency().getCode());
                    response.setCurrencyName(exchangeRate.getCurrency().getName());
                    response.setFlagIcon(exchangeRate.getCurrency().getFlagIcon());
                    response.setBuyRate(exchangeRate.getBuyRate());
                    response.setSellRate(exchangeRate.getSellRate());
                    response.setCreatedAt(String.valueOf(exchangeRate.getCreatedAt()));
                    return response;
                })
                .collect(Collectors.toList());
    }
}
