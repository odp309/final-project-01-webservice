package com.bni.finalproject01webservice.dto.sell_valas.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class SellValasResponseDTO {

    private BigDecimal amountToReceive;
    private BigDecimal amountToSell;
    private String currencyCode;
    private String currencyName;
    private String accountNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MMMM yyyy HH:mm", timezone = "Asia/Jakarta")
    private Date createdAt;
}
