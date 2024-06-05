package com.bni.finalproject01webservice.dto.sell_valas.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SellValasResponseDTO {

    public BigDecimal amountToPay;
    public BigDecimal amountToSell;
    public String currencyCode;
    public String currencyName;
    public String accountNumber;

}
