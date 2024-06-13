package com.bni.finalproject01webservice.dto.branch_reserve_log.request;

import com.bni.finalproject01webservice.model.BranchReserve;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BranchReserveLogRequestDTO {

    private BigDecimal amount;
    private BigDecimal currentBalance;
    private BigDecimal updatedBalance;
    private String updatedBy;
    private String operationTypeName;
    private BranchReserve branchReserve;
}
