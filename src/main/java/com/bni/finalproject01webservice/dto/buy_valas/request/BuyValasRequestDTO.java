package com.bni.finalproject01webservice.dto.buy_valas.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class BuyValasRequestDTO {

    private UUID walletId;
    private BigDecimal amountToBuy;
    private String pin;
}
