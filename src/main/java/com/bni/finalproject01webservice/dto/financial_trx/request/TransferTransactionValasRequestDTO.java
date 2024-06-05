package com.bni.finalproject01webservice.dto.financial_trx.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class TransferTransactionValasRequestDTO {

    private UUID walletId;
    private String fullName;
    private String type;
    private BigDecimal amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "Asia/Jakarta")
    private Date date;
}