package com.bni.finalproject01webservice.dto.history.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
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



    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Jakarta")
    private Date createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Jakarta")
    private Date reservationDate;

}
