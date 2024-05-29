package com.bni.finalproject01webservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ExchangeRateResponseDTO {

    private String currencyCode;
    private BigDecimal buyRate;
    private BigDecimal sellRate;
    private Date createdAt;
}
