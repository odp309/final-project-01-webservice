package com.bni.finalproject01webservice.dto.exchange_rate.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ExchangeRateResponseDTO {

    private String currencyCode;
    private String currencyName;
    private String flagIcon;
    private BigDecimal buyRate;
    private BigDecimal sellRate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Jakarta")
    private Date createdAt;
}
