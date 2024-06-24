package com.bni.finalproject01webservice.dto.history.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class HistoryDataResponseDTO {

    private UUID trxId;
    private BigDecimal Amount;
    private String trxType;
    private String status;
    private String currencyCode;
    private String operationType;
    private String createdDate;
}