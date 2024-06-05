package com.bni.finalproject01webservice.dto.bank_account.response;

import com.bni.finalproject01webservice.dto.wallet.response.SecureWalletResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetBankAccountWalletResponseDTO {

    private String firstName;
    private String lastName;
    private SecureWalletResponseDTO wallet;
}
