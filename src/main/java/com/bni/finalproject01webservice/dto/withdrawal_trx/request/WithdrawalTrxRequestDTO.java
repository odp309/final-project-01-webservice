package com.bni.finalproject01webservice.dto.withdrawal_trx.request;

import com.bni.finalproject01webservice.model.User;
import com.bni.finalproject01webservice.model.Wallet;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WithdrawalTrxRequestDTO {

    private User user;
    private Wallet wallet;
    private String trxTypeName;
    private String operationTypeName;
    private String branchCode;
    private BigDecimal amount;
}