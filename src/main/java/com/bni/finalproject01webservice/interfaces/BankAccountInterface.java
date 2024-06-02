package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.request.AddBankAccountRequestDTO;
import com.bni.finalproject01webservice.dto.request.GetAllBankAccountRequestDTO;
import com.bni.finalproject01webservice.dto.request.GetBankAccountRequestDTO;
import com.bni.finalproject01webservice.dto.request.GetBankAccountWalletRequestDTO;
import com.bni.finalproject01webservice.dto.response.BankAccountResponseDTO;
import com.bni.finalproject01webservice.dto.response.GetBankAccountWalletResponseDTO;

import java.util.List;

public interface BankAccountInterface {

    BankAccountResponseDTO addBankAccount(AddBankAccountRequestDTO request);

    BankAccountResponseDTO getBankAccount(GetBankAccountRequestDTO request);

    List<BankAccountResponseDTO> getAllBankAccountOfUser(GetAllBankAccountRequestDTO request);

    GetBankAccountWalletResponseDTO getBankAccountWallet(GetBankAccountWalletRequestDTO request);
}
