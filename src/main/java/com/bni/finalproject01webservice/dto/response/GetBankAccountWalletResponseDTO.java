package com.bni.finalproject01webservice.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetBankAccountWalletResponseDTO {

    private String firstName;
    private String lastName;
    private SecureWalletResponseDTO wallet;
}
