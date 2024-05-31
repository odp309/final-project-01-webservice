package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.request.AddBankAccountRequestDTO;
import com.bni.finalproject01webservice.dto.response.BankAccountResponseDTO;

import java.util.UUID;

public interface BankAccountInterface {

    BankAccountResponseDTO getSaldo(String accountNumber);

    BankAccountResponseDTO addBankAccount(AddBankAccountRequestDTO request);
}
