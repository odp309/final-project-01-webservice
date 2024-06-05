package com.bni.finalproject01webservice.dto.bank_account.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BankAccountResponseDTO {

    private String accountNumber;
    private String type;
    private BigDecimal balance;
}
