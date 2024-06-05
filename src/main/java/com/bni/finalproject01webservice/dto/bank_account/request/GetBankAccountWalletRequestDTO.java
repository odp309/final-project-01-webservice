package com.bni.finalproject01webservice.dto.bank_account.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetBankAccountWalletRequestDTO {

    private String accountNumber;
    private String currencyCode;
}
