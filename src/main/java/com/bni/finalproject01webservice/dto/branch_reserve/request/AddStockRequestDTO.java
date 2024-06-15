package com.bni.finalproject01webservice.dto.branch_reserve.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AddStockRequestDTO {

    private String branchCode; // unused
    private String currencyCode;
    private BigDecimal amount;
}
