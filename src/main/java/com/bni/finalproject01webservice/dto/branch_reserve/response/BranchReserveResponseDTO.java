package com.bni.finalproject01webservice.dto.branch_reserve.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BranchReserveResponseDTO {

    private String branchName;
    private String currencyCode;
    private BigDecimal balance;
    private BigDecimal tempBalance;
}
