package com.bni.finalproject01webservice.dto.wallet.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletWithCurrencyIconResponseDTO {

    private Integer id;
    private String currencyCode;
    private String currencyName;
    private BigDecimal balance;
    private String icon;
}
