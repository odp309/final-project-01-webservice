package com.bni.finalproject01webservice.dto.transfer_valas.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DetailTransferValasResponseDTO {

    private String firstName;
    private String lastName;
    private String currencyCode;
    private BigDecimal amountToTransfer;
}
