package com.bni.finalproject01webservice.dto.exchange_rate.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class FrankfurterRequestDTO {

    private String base;
    private Map<String, BigDecimal> rates;
}
