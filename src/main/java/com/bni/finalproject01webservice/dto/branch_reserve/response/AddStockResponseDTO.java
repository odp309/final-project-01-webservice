package com.bni.finalproject01webservice.dto.branch_reserve.response;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddStockResponseDTO {

    private String message;
    private UUID branchReserveLogId;
}
