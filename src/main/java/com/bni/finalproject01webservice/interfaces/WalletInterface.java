package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.wallet.request.AddWalletRequestDTO;
import com.bni.finalproject01webservice.dto.wallet.response.GetAllWalletResponseDTO;
import com.bni.finalproject01webservice.dto.wallet.response.WalletResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface WalletInterface {

    GetAllWalletResponseDTO getWallet(String accountNumber);

    WalletResponseDTO addWallet(AddWalletRequestDTO request);

    //////////////////////////////// VERSION 2.0 BLOCK ////////////////////////////////

    WalletResponseDTO addWallet(AddWalletRequestDTO request, HttpServletRequest headerRequest);
}
