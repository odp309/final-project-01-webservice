package com.bni.finalproject01webservice.dto.sell_valas.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class DetailSellValasRequestDTO {

    private UUID walletId;
    private BigDecimal amountToSell;
}
