package com.bni.finalproject01webservice.dto.exchange_rate.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class FrankfurterResponseDTO {

    private String base;
    private Map<String, BigDecimal> buyRate;
    private Map<String, BigDecimal> sellRate;
}
