package com.bni.finalproject01webservice.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class AddBankAccountRequestDTO {

    private UUID userId;
    private String accountNumber;
    private BigDecimal balance;
}
