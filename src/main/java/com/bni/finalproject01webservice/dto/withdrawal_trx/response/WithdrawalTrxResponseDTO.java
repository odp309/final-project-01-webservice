package com.bni.finalproject01webservice.dto.withdrawal_trx.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class WithdrawalTrxResponseDTO {

    private UUID withdrawalTrxId;
    private String reservationNumber;
}
