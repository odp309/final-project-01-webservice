package com.bni.finalproject01webservice.dto.buy_valas.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BuyValasResponseDTO {

    private BigDecimal amountToPay;
    private BigDecimal amountToBuy;
    private String currencyCode;
    private String currencyName;
    private String accountNumber;
    private String createdAt;
}
