package com.bni.finalproject01webservice.dto.buy_valas.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DetailBuyValasResponseDTO {

    private String currencyCode;
    private String currencyName;
    private BigDecimal buyRate;
    private BigDecimal totalAmountToBuy;
}
