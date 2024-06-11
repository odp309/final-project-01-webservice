package com.bni.finalproject01webservice.dto.reservation_list.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ReservationListResponseDTO {
    private String reservationNumber;
    private String customerName;
    private String accountNumber;
    private String currencyCode;
    private BigDecimal amount;
    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Jakarta")
    private Date reservationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Jakarta")
    private Date createdDate;
}
