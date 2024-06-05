package com.bni.finalproject01webservice.dto.buy_valas.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DetailBuyValasResponseDTO {

    public String currencyCode;
    public String currencyName;
    public BigDecimal buyRate;
    public BigDecimal totalAmountToBuy;
}
