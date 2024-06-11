package com.bni.finalproject01webservice.dto.withdraw_valas.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class WithdrawValasRequestDTO {

    private UUID walletId;
    private BigDecimal amountToWithdraw;
    private Date reservationDate;
    private String branchCode;
    private String pin;
}
