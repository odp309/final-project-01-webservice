package com.bni.finalproject01webservice.dto.history.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class HistoryDetailRequestDTO {
    private UUID trxId;
}
