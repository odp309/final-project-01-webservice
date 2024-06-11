package com.bni.finalproject01webservice.dto.withdraw_valas.response;

import com.bni.finalproject01webservice.dto.trx_history.response.TrxHistoryResponseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class WithdrawValasResponseDTO {

    private BigDecimal amountToWithdraw;
    private String branchType;
    private String branchName;
    private String branchAddress;
    private String branchCity;
    private String branchProvince;
    private String currencyCode;
    private String reservationNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MMMM yyyy", timezone = "Asia/Jakarta")
    private Date reservationDate;

    private TrxHistoryResponseDTO trxHistory;
}
