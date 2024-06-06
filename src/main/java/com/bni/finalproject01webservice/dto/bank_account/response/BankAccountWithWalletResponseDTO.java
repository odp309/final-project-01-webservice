package com.bni.finalproject01webservice.dto.bank_account.response;

import com.bni.finalproject01webservice.dto.wallet.response.WalletWithCurrencyIconResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class BankAccountWithWalletResponseDTO {

    private Integer id;
    private String accountNumber;
    private String type;
    private BigDecimal balance;
    private List<WalletWithCurrencyIconResponseDTO> listWallet;
}
