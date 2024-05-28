package com.bni.finalproject01webservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class ExchangeRateDTO {

    private String base;
    private Map<String, BigDecimal> rates;
    private Map<String, BigDecimal> buyRates;
    private Map<String, BigDecimal> sellRates;
}
