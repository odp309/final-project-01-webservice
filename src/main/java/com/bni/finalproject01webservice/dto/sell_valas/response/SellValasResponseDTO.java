package com.bni.finalproject01webservice.dto.sell_valas.response;

import com.bni.finalproject01webservice.dto.trx_history.response.TrxHistoryResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SellValasResponseDTO {

    private BigDecimal amountToReceive;
    private BigDecimal amountToSell;
    private String currencyCode;
    private String currencyName;
    private String accountNumber;
    private TrxHistoryResponseDTO trxHistory;
}
