package com.bni.finalproject01webservice.dto.wallet.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class WalletWithCurrencyIconResponseDTO {

    private Integer id;
    private UUID walletId;
    private String currencyCode;
    private String currencyName;
    private BigDecimal balance;
    private String flagIcon;
    private String landmarkIcon;
}
