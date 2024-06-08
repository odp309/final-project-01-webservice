package com.bni.finalproject01webservice.dto.buy_valas.response;

import com.bni.finalproject01webservice.dto.trx_history.response.TrxHistoryResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BuyValasResponseDTO {

    public BigDecimal amountToPay;
    public BigDecimal amountToBuy;
    public String currencyCode;
    public String currencyName;
    public String accountNumber;
    public TrxHistoryResponseDTO trxHistory;
}
