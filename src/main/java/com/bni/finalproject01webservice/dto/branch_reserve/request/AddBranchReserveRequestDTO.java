package com.bni.finalproject01webservice.dto.branch_reserve.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBranchReserveRequestDTO {

    private String branchCode;
    private String currencyCode;
}
