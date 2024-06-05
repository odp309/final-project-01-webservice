package com.bni.finalproject01webservice.dto.financial_trx.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionValasResponseDTO {

    private BigDecimal amount;
    private String type;
    private String description;
}
