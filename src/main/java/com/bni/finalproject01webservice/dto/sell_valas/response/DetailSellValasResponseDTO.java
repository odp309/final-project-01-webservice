package com.bni.finalproject01webservice.dto.sell_valas.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DetailSellValasResponseDTO {

    private String currencyCode;
    private String currencyName;
    private BigDecimal sellRate;
    private BigDecimal totalAmountToSell;
}
