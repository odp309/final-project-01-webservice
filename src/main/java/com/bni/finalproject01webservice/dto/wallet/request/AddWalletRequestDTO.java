package com.bni.finalproject01webservice.dto.wallet.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class AddWalletRequestDTO {

    private UUID userId; // unused
    private String accountNumber;
    private String currencyCode;
    private BigDecimal amountToBuy;
    private String pin;
}
