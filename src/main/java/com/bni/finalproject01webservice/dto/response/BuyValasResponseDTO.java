package com.bni.finalproject01webservice.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BuyValasResponseDTO {

    public BigDecimal amountToPay;
    public BigDecimal amountToBuy;
    public String currencyCode;
    public String currencyName;
    public String accountNumber;
}
