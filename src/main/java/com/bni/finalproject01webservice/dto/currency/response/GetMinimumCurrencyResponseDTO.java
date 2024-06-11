package com.bni.finalproject01webservice.dto.currency.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class GetMinimumCurrencyResponseDTO {

    private BigDecimal minimum;
}
