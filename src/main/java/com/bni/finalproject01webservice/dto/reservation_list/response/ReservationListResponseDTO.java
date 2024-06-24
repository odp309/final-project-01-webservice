package com.bni.finalproject01webservice.dto.reservation_list.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReservationListResponseDTO {
    private String reservationNumber;
    private String customerName;
    private String accountNumber;
    private String currencyCode;
    private BigDecimal amount;
    private String status;
    private String doneBy;

    private String reservationDate;

    private String createdDate;
}
