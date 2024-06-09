package com.bni.finalproject01webservice.dto.branch_reserve.request;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class InitBranchReserveRequestDTO {
    private String name;
    private String currencyCode;
}
