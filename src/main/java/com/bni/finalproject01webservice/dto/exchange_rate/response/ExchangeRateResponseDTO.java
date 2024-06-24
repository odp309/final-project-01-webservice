package com.bni.finalproject01webservice.dto.exchange_rate.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ExchangeRateResponseDTO {

    private String currencyCode;
    private String currencyName;
    private String flagIcon;
    private BigDecimal buyRate;
    private BigDecimal sellRate;
    private String createdAt;
}
