package com.bni.finalproject01webservice.dto.branch.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BranchRequestDTO {

    private double latitude;
    private double longitude;
    private BigDecimal amountToWithdraw;
    private String currencyCode;
}
