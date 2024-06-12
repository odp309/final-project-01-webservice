package com.bni.finalproject01webservice.dto.withdrawal_detail.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class WithdrawalDetailResponseDTO {

    private UUID withdrawalDetail;
    private String detail;
}
