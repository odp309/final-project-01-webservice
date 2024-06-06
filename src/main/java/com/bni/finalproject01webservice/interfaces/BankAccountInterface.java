package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.bank_account.request.AddBankAccountRequestDTO;
import com.bni.finalproject01webservice.dto.bank_account.request.GetAllBankAccountRequestDTO;
import com.bni.finalproject01webservice.dto.bank_account.request.GetBankAccountRequestDTO;
import com.bni.finalproject01webservice.dto.bank_account.request.GetBankAccountWalletRequestDTO;
import com.bni.finalproject01webservice.dto.bank_account.response.BankAccountResponseDTO;
import com.bni.finalproject01webservice.dto.bank_account.response.BankAccountWithWalletResponseDTO;
import com.bni.finalproject01webservice.dto.bank_account.response.GetBankAccountWalletResponseDTO;

import java.util.List;

public interface BankAccountInterface {

    BankAccountResponseDTO addBankAccount(AddBankAccountRequestDTO request);

    BankAccountResponseDTO getBankAccount(GetBankAccountRequestDTO request);

    List<BankAccountWithWalletResponseDTO> getAllBankAccountOfUser(GetAllBankAccountRequestDTO request);

    GetBankAccountWalletResponseDTO getBankAccountWallet(GetBankAccountWalletRequestDTO request);
}
