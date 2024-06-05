package com.bni.finalproject01webservice.dto.financial_trx.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class TradeTransactionValasRequestDTO {

    private UUID walletId;
    private String type;
    private String currencyCode;
    private BigDecimal amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Jakarta")
    private Date date;
}