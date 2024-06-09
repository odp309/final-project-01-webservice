package com.bni.finalproject01webservice.dto.buy_valas.response;

import com.bni.finalproject01webservice.dto.trx_history.response.TrxHistoryResponseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class BuyValasResponseDTO {

    private BigDecimal amountToPay;
    private BigDecimal amountToBuy;
    private String currencyCode;
    private String currencyName;
    private String accountNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MMMM yyyy HH:mm", timezone = "Asia/Jakarta")
    private Date createdAt;

    private TrxHistoryResponseDTO trxHistory;
}
