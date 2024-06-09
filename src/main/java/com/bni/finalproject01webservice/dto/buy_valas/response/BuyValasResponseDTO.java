package com.bni.finalproject01webservice.dto.buy_valas.response;

import com.bni.finalproject01webservice.dto.trx_history.response.TrxHistoryResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BuyValasResponseDTO {

    private BigDecimal amountToPay;
    private BigDecimal amountToBuy;
    private String currencyCode;
    private String currencyName;
    private String accountNumber;
    private TrxHistoryResponseDTO trxHistory;
}
