package com.bni.finalproject01webservice.dto.wallet.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetAllWalletResponseDTO {
    private String accountNumber;

    private List<WalletInfo> wallet;

    @Getter
    @Setter
    public static class WalletInfo {
        private String currencyId;
        private float balance;
    }
}
