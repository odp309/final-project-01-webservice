package com.bni.finalproject01webservice.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class BuyValasRequestDTO {

    public UUID walletId;
    public BigDecimal amountToBuy;
    public String currencyCode;
    private String accountNumber;
   // public BigDecimal amountToPay;
    private String pin;
}
