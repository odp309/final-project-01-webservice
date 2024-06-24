package com.bni.finalproject01webservice.dto.history.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class HistoryDetailResponseDTO {

    private UUID trxId;
    private BigDecimal Amount;
    private BigDecimal paidPrice;
    private BigDecimal kurs;
    private String trxType;
    private String status;
    private String currencyCode;
    private String Detail;
    private String operationType;
    private String accountName;
    private String reservationCode;
    private String createdDate;
    private String reservationDate;
}
