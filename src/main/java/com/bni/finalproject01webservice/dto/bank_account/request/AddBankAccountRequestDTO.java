package com.bni.finalproject01webservice.dto.bank_account.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class AddBankAccountRequestDTO {

    private UUID userId; // unused
    private String accountNumber;
    private String type;
    private BigDecimal balance;
}
