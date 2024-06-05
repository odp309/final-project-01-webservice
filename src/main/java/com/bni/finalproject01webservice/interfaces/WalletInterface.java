package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.wallet.request.AddWalletRequestDTO;
import com.bni.finalproject01webservice.dto.wallet.response.GetAllWalletResponseDTO;
import com.bni.finalproject01webservice.dto.wallet.response.WalletResponseDTO;

public interface WalletInterface {

    GetAllWalletResponseDTO getWallet(String accountNumber);

    WalletResponseDTO addWallet(AddWalletRequestDTO request);
}
