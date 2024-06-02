package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.request.AddWalletRequestDTO;
import com.bni.finalproject01webservice.dto.response.GetAllWalletResponseDTO;
import com.bni.finalproject01webservice.dto.response.WalletResponseDTO;

public interface WalletInterface {

    GetAllWalletResponseDTO getWallet(String accountNumber);

    WalletResponseDTO addWallet(AddWalletRequestDTO request);
}
