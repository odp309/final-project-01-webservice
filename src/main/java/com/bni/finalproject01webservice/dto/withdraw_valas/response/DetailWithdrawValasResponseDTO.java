package com.bni.finalproject01webservice.dto.withdraw_valas.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DetailWithdrawValasResponseDTO {

    private String branchName;
    private String branchTypeFull;
    private String branchType;
    private String currencyCode;
    private BigDecimal amountToWithdraw;
    private String reservationDate;
}