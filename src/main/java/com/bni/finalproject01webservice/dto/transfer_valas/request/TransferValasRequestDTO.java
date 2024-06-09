package com.bni.finalproject01webservice.dto.transfer_valas.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class TransferValasRequestDTO {

    private UUID senderWalletId;
    private String recipientAccountNumber;
    private BigDecimal amountToTransfer;
    private String pin;
}
