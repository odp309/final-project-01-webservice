package com.bni.finalproject01webservice.dto.sell_valas.response;

import com.bni.finalproject01webservice.dto.trx_history.response.TrxHistoryResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SellValasResponseDTO {

    public BigDecimal amountToReceive;
    public BigDecimal amountToSell;
    public String currencyCode;
    public String currencyName;
    public String accountNumber;
    public TrxHistoryResponseDTO trxHistory;
}
