package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.configuration.exceptions.WalletException;
import com.bni.finalproject01webservice.dto.bank_account.request.AddBankAccountRequestDTO;
import com.bni.finalproject01webservice.dto.bank_account.request.GetAllBankAccountRequestDTO;
import com.bni.finalproject01webservice.dto.bank_account.request.GetBankAccountRequestDTO;
import com.bni.finalproject01webservice.dto.bank_account.request.GetBankAccountWalletRequestDTO;
import com.bni.finalproject01webservice.dto.bank_account.response.BankAccountResponseDTO;
import com.bni.finalproject01webservice.dto.bank_account.response.GetBankAccountWalletResponseDTO;
import com.bni.finalproject01webservice.dto.wallet.response.SecureWalletResponseDTO;
import com.bni.finalproject01webservice.model.BankAccount;
import com.bni.finalproject01webservice.model.User;
import com.bni.finalproject01webservice.model.Wallet;
import com.bni.finalproject01webservice.repository.BankAccountRepository;
import com.bni.finalproject01webservice.interfaces.BankAccountInterface;
import com.bni.finalproject01webservice.repository.UserRepository;
import com.bni.finalproject01webservice.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankAccountService implements BankAccountInterface {

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

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

    @Override
    public GetBankAccountWalletResponseDTO getBankAccountWallet(GetBankAccountWalletRequestDTO request) {
        Wallet currentWallet = walletRepository.findByBankAccountAccountNumberAndCurrencyCode(request.getAccountNumber(), request.getCurrencyCode());

        if (currentWallet == null) {
            throw new WalletException("Wallet not found!");
        }

        SecureWalletResponseDTO walletResponse = new SecureWalletResponseDTO();
        walletResponse.setAccountNumber(currentWallet.getBankAccount().getAccountNumber());
        walletResponse.setCurrencyCode(currentWallet.getCurrency().getCode());

        GetBankAccountWalletResponseDTO response = new GetBankAccountWalletResponseDTO();
        response.setFirstName(currentWallet.getUser().getFirstName());
        response.setLastName(currentWallet.getUser().getLastName());
        response.setWallet(walletResponse);

        return response;
    }
}
