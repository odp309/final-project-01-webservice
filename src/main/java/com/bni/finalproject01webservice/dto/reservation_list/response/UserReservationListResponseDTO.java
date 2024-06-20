package com.bni.finalproject01webservice.dto.reservation_list.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Jakarta")
    private Date reservationDate;
}
