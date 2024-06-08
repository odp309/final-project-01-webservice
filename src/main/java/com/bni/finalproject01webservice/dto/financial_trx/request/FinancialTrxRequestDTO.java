package com.bni.finalproject01webservice.dto.financial_trx.request;

import com.bni.finalproject01webservice.model.User;
import com.bni.finalproject01webservice.model.Wallet;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FinancialTrxRequestDTO {

    private User user;
    private Wallet wallet;
    private String trxTypeName;
    private String operationTypeName;
    private String accountNumber; // for Transfer
    private BigDecimal rate; // for Beli, and Jual
    private BigDecimal amount;
}