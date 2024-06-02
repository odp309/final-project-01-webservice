package com.bni.finalproject01webservice.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class BankAccountResponseDTO {

    private String accountNumber;
    private String type;
    private BigDecimal balance;
}
