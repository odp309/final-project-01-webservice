package com.bni.finalproject01webservice.dto.withdraw_valas.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class DetailWithdrawValasResponseDTO {

    private String branchName;
    private String branchTypeFull;
    private String branchType;
    private String currencyCode;
    private BigDecimal amountToWithdraw;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MMMM yyyy", timezone = "Asia/Jakarta")
    private Date reservationDate;
}