package com.bni.finalproject01webservice.dto.branch_reserve.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class GetBranchReserveResponseDTO {

    private String currencyCode;
    private String currencyName;
    private BigDecimal balance;
    private String updatedAt;
}
