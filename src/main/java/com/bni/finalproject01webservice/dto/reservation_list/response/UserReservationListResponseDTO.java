package com.bni.finalproject01webservice.dto.reservation_list.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UserReservationListResponseDTO {

    private String reservationNumber;
    private String currencyCode;
    private BigDecimal amount;
    private String branchType;
    private String branchName;
    private String branchAddress;
    private String status;
    private String reservationDate;
}
