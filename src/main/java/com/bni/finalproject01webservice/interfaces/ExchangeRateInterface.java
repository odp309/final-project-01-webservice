package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.ExchangeRateDTO;
import com.bni.finalproject01webservice.dto.ExchangeRateResponseDTO;

import java.util.List;

public interface ExchangeRateInterface {

    ExchangeRateDTO getCurrency();
    ExchangeRateDTO getCurrencySpecific(String baseCurrency);
    List<ExchangeRateResponseDTO> getAllExchangeRates();
}
