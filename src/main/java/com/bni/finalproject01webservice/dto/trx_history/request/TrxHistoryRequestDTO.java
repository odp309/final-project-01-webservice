package com.bni.finalproject01webservice.dto.trx_history.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TrxHistoryRequestDTO {

    private UUID financialTrxId;
    private UUID withdrawalTrxId;
}
