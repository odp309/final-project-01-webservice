package com.bni.finalproject01webservice.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class SellValasRequestDTO {

    private UUID walletId;
    private BigDecimal amountToBuy;
    private String pin;

}
