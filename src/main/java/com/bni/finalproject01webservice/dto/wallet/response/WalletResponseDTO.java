package com.bni.finalproject01webservice.dto.wallet.response;

import com.bni.finalproject01webservice.dto.buy_valas.response.BuyValasResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class WalletResponseDTO {

    private UUID userId;
    private BigDecimal currentBalance;
    private BuyValasResponseDTO buyValasResponse;
}
