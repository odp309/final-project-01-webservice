package com.bni.finalproject01webservice.dto.wallet.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class WalletResponseDTO {

    private String accountNumber;
    private String currencyCode;
    private UUID userId;
    private BigDecimal balance;
}
