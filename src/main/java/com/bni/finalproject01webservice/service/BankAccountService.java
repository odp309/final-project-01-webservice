package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.request.AddBankAccountRequestDTO;
import com.bni.finalproject01webservice.dto.request.GetAllBankAccountRequestDTO;
import com.bni.finalproject01webservice.dto.request.GetBankAccountRequestDTO;
import com.bni.finalproject01webservice.dto.response.AddBankAccountResponseDTO;
import com.bni.finalproject01webservice.dto.response.BankAccountResponseDTO;
import com.bni.finalproject01webservice.dto.response.ExchangeRateResponseDTO;
import com.bni.finalproject01webservice.dto.response.RegisterResponseDTO;
import com.bni.finalproject01webservice.model.BankAccount;
import com.bni.finalproject01webservice.model.User;
import com.bni.finalproject01webservice.repository.BankAccountRepository;
import com.bni.finalproject01webservice.interfaces.BankAccountInterface;
import com.bni.finalproject01webservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankAccountService implements BankAccountInterface {

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;

    @Override
    public BankAccountResponseDTO addBankAccount(AddBankAccountRequestDTO request) {
        Optional<User> user = userRepository.findById(request.getUserId());

        if (user.isPresent()) {
            User userObj = user.get();
            BankAccount newBankAccount = new BankAccount();
            newBankAccount.setUser(userObj);
            newBankAccount.setAccountNumber(request.getAccountNumber());
            newBankAccount.setType(request.getType());
            newBankAccount.setBalance(request.getBalance());
            newBankAccount.setCreatedAt(new Date());
            bankAccountRepository.save(newBankAccount);
        } else {
            throw new RuntimeException("User not found");
        }

        BankAccountResponseDTO response = new BankAccountResponseDTO();
        response.setAccountNumber(request.getAccountNumber());
        response.setType(request.getType());
        response.setBalance(request.getBalance());

        return response;
    }

    @Override
    public BankAccountResponseDTO getBankAccount(GetBankAccountRequestDTO request) {
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(request.getAccountNumber());
        if (bankAccount == null) {
            throw new RuntimeException("Bank Account not found");
        }
        BankAccountResponseDTO response = new BankAccountResponseDTO();
        response.setAccountNumber(bankAccount.getAccountNumber());
        response.setType(bankAccount.getType());
        response.setBalance(bankAccount.getBalance());
        return response;
    }

    @Override
    public List<BankAccountResponseDTO> getAllBankAccountOfUser(GetAllBankAccountRequestDTO request) {
        List<BankAccount> bankAccounts = bankAccountRepository.findByUserId(request.getUserId());

        return bankAccounts.stream()
                .map(bankAccount -> {
                    BankAccountResponseDTO response = new BankAccountResponseDTO();
                    response.setAccountNumber(bankAccount.getAccountNumber());
                    response.setType(bankAccount.getType());
                    response.setBalance(bankAccount.getBalance());
                    return response;
                })
                .collect(Collectors.toList());
    }
}
