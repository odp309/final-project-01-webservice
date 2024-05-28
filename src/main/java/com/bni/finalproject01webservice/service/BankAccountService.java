package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.BankAccountResponseDTO;
import com.bni.finalproject01webservice.model.BankAccount;
import com.bni.finalproject01webservice.repository.BankAccountRepository;
import com.bni.finalproject01webservice.interfaces.BankAccountInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService implements BankAccountInterface {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    public BankAccountResponseDTO getSaldo(String accountNumber) {
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(accountNumber);
        if (bankAccount == null) {
            throw new RuntimeException("BankAccount not found");
        }
        BankAccountResponseDTO response = new BankAccountResponseDTO();
        response.setAccountNumber(bankAccount.getAccountNumber());
        response.setId_user(bankAccount.getUser().getId());
        response.setBalance(bankAccount.getBalance());
        return response;
    }

}
