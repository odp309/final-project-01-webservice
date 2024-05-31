package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.response.WalletResponseDTO;

public interface WalletInterface {
    WalletResponseDTO getWallet(String accountNumber);
}
