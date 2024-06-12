package com.bni.finalproject01webservice.dto.withdrawal.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class WithdrawalResponseDTO {

    private UUID withdrawalId;
    private String reservationNumber;
}
