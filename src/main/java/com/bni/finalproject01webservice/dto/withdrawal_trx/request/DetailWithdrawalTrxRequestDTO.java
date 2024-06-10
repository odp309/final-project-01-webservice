package com.bni.finalproject01webservice.dto.withdrawal_trx.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class DetailWithdrawalTrxRequestDTO {

    private UUID walletId;
    private BigDecimal amountToWithdraw;
    private Date reservationDate;
    private UUID branchId;
}
