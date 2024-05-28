package com.bni.finalproject01webservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class BankAccountResponseDTO {

    private String accountNumber;
    private UUID id_user;
    private BigDecimal balance;
}
