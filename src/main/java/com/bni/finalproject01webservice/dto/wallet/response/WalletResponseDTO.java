package com.bni.finalproject01webservice.dto.wallet.response;

import com.bni.finalproject01webservice.dto.buy_valas.response.BuyValasResponseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class WalletResponseDTO {

    private UUID userId;
    private BigDecimal currentBalance;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MMMM yyyy HH:mm", timezone = "Asia/Jakarta")
    private Date createdAt;

    private BuyValasResponseDTO buyValasResponse;
}
