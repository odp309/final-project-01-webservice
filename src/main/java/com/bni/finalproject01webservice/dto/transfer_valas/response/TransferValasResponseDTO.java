package com.bni.finalproject01webservice.dto.transfer_valas.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class TransferValasResponseDTO {

    private BigDecimal amountToTransfer;
    private String currencyCode;
    private String recipientFirstName;
    private String recipientLastName;
    private String recipientAccountNumber;
    private String createdAt;
}
