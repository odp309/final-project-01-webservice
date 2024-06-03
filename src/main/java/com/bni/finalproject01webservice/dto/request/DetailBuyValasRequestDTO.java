package com.bni.finalproject01webservice.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class DetailBuyValasRequestDTO {
    public BigDecimal amountToBuy;
    public String currencyCode;


}
