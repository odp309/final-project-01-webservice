package com.bni.finalproject01webservice.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class DetailSellValasRequestDTO {

    public BigDecimal amountToSell;
    public String currencyCode;
}
