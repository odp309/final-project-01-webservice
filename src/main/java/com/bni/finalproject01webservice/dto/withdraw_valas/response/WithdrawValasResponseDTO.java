package com.bni.finalproject01webservice.dto.withdraw_valas.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WithdrawValasResponseDTO {

    private BigDecimal amountToWithdraw;
    private String branchType;
    private String branchName;
    private String branchAddress;
    private String branchCity;
    private String branchProvince;
    private String currencyCode;
    private String reservationCode;
    private String reservationDate;
}
