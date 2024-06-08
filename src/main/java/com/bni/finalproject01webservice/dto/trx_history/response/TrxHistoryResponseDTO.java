package com.bni.finalproject01webservice.dto.trx_history.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TrxHistoryResponseDTO {

    private UUID financialTrxId;
    private UUID withdrawalTrxId;
}
