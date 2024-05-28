package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.ExchangeRateDTO;

public interface ExchangeRateInterface {

    ExchangeRateDTO getCurrency();
    ExchangeRateDTO getCurrencySpecific(String baseCurrency);
}
