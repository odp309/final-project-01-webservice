package com.bni.finalproject01webservice.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DetailSellValasResponseDTO {
    public String currencyCode;
    public String currencyName;
    public BigDecimal sellRate;
    public BigDecimal totalAmountToSell;
}
