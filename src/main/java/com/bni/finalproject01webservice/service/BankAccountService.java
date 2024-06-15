package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.configuration.exceptions.TransactionException;
import com.bni.finalproject01webservice.configuration.exceptions.UserException;
import com.bni.finalproject01webservice.configuration.exceptions.WalletException;
import com.bni.finalproject01webservice.dto.bank_account.request.AddBankAccountRequestDTO;
import com.bni.finalproject01webservice.dto.bank_account.request.GetAllBankAccountRequestDTO;
import com.bni.finalproject01webservice.dto.bank_account.request.GetBankAccountRequestDTO;
import com.bni.finalproject01webservice.dto.bank_account.request.GetBankAccountWalletRequestDTO;
import com.bni.finalproject01webservice.dto.bank_account.response.BankAccountResponseDTO;
import com.bni.finalproject01webservice.dto.bank_account.response.BankAccountWithWalletResponseDTO;
import com.bni.finalproject01webservice.dto.bank_account.response.GetBankAccountWalletResponseDTO;
import com.bni.finalproject01webservice.dto.wallet.response.SecureWalletResponseDTO;
import com.bni.finalproject01webservice.dto.wallet.response.WalletWithCurrencyIconResponseDTO;
import com.bni.finalproject01webservice.interfaces.JWTInterface;
import com.bni.finalproject01webservice.interfaces.ResourceRequestCheckerInterface;
import com.bni.finalproject01webservice.model.BankAccount;
import com.bni.finalproject01webservice.model.User;
import com.bni.finalproject01webservice.model.Wallet;
import com.bni.finalproject01webservice.repository.BankAccountRepository;
import com.bni.finalproject01webservice.interfaces.BankAccountInterface;
import com.bni.finalproject01webservice.repository.UserRepository;
import com.bni.finalproject01webservice.repository.WalletRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankAccountService implements BankAccountInterface {

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    private final JWTInterface jwtService;
    private final ResourceRequestCheckerInterface resourceRequestCheckerService;

    @Override
    public BankAccountResponseDTO addBankAccount(AddBankAccountRequestDTO request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new UserException("User not found!"));

        BankAccount newBankAccount = new BankAccount();
        newBankAccount.setUser(user);
        newBankAccount.setAccountNumber(request.getAccountNumber());
        newBankAccount.setType(request.getType());
        newBankAccount.setBalance(request.getBalance());
        newBankAccount.setCreatedAt(new Date());
        bankAccountRepository.save(newBankAccount);

        BankAccountResponseDTO response = new BankAccountResponseDTO();
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
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
        response.setFirstName(bankAccount.getUser().getFirstName());
        response.setLastName(bankAccount.getUser().getLastName());
        response.setAccountNumber(bankAccount.getAccountNumber());
        response.setType(bankAccount.getType());
        response.setBalance(bankAccount.getBalance());

        return response;
    }

    @Override
    public List<BankAccountWithWalletResponseDTO> getAllBankAccountOfUser(GetAllBankAccountRequestDTO request) {
        List<BankAccount> bankAccounts = bankAccountRepository.findByUserId(request.getUserId());

        return bankAccounts.stream()
                .map(bankAccount -> {
                    int idBankAccountCount = bankAccounts.indexOf(bankAccount) + 1;
                    List<Wallet> wallets = walletRepository.findByBankAccountAccountNumber(bankAccount.getAccountNumber());
                    BankAccountWithWalletResponseDTO response = new BankAccountWithWalletResponseDTO();
                    response.setId(idBankAccountCount);
                    response.setAccountNumber(bankAccount.getAccountNumber());
                    response.setType(bankAccount.getType());
                    response.setBalance(bankAccount.getBalance());
                    response.setListWallet(wallets.stream()
                            .map(wallet -> {
                                int idWalletCount = wallets.indexOf(wallet) + 1;
                                WalletWithCurrencyIconResponseDTO walletResponse = new WalletWithCurrencyIconResponseDTO();
                                walletResponse.setId(idWalletCount);
                                walletResponse.setWalletId(wallet.getId());
                                walletResponse.setCurrencyCode(wallet.getCurrency().getCode());
                                walletResponse.setCurrencyName(wallet.getCurrency().getName());
                                walletResponse.setBalance(wallet.getBalance());
                                walletResponse.setFlagIcon(wallet.getCurrency().getFlagIcon());
                                walletResponse.setLandmarkIcon(wallet.getCurrency().getLandmarkIcon());
                                return walletResponse;
                            }).collect(Collectors.toList()));
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public GetBankAccountWalletResponseDTO getBankAccountWallet(GetBankAccountWalletRequestDTO request) {

        if (request.getSenderAccountNumber().equals(request.getRecipientAccountNumber())) {
            throw new TransactionException("Transfers to the same account are not allowed!");
        }

        Wallet currentWallet = walletRepository.findByBankAccountAccountNumberAndCurrencyCode(request.getRecipientAccountNumber(), request.getCurrencyCode());

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

    //////////////////////////////// VERSION 2.0 BLOCK ////////////////////////////////

    @Override
    public BankAccountResponseDTO addBankAccount(AddBankAccountRequestDTO request, HttpServletRequest headerRequest) {

        UUID userId = resourceRequestCheckerService.extractUserIdFromToken(headerRequest);

        User user = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found!"));

        BankAccount newBankAccount = new BankAccount();
        newBankAccount.setUser(user);
        newBankAccount.setAccountNumber(request.getAccountNumber());
        newBankAccount.setType(request.getType());
        newBankAccount.setBalance(request.getBalance());
        newBankAccount.setCreatedAt(new Date());
        bankAccountRepository.save(newBankAccount);

        BankAccountResponseDTO response = new BankAccountResponseDTO();
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setAccountNumber(request.getAccountNumber());
        response.setType(request.getType());
        response.setBalance(request.getBalance());

        return response;
    }

    @Override
    public BankAccountResponseDTO getBankAccount(GetBankAccountRequestDTO request, HttpServletRequest headerRequest) {

        // bank account request checking
        List<String> bankAccountNumbers = resourceRequestCheckerService.bankAccountChecker(headerRequest);
        if (!bankAccountNumbers.contains(request.getAccountNumber())) {
            throw new UserException("Un-match request!");
        }

        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(request.getAccountNumber());

        if (bankAccount == null) {
            throw new RuntimeException("Bank Account not found");
        }

        BankAccountResponseDTO response = new BankAccountResponseDTO();
        response.setFirstName(bankAccount.getUser().getFirstName());
        response.setLastName(bankAccount.getUser().getLastName());
        response.setAccountNumber(bankAccount.getAccountNumber());
        response.setType(bankAccount.getType());
        response.setBalance(bankAccount.getBalance());

        return response;
    }

    @Override
    public List<BankAccountWithWalletResponseDTO> getAllBankAccountOfUser(GetAllBankAccountRequestDTO request, HttpServletRequest headerRequest) {

        UUID userId = resourceRequestCheckerService.extractUserIdFromToken(headerRequest);

        List<BankAccount> bankAccounts = bankAccountRepository.findByUserId(userId);

        return bankAccounts.stream()
                .map(bankAccount -> {
                    int idBankAccountCount = bankAccounts.indexOf(bankAccount) + 1;
                    List<Wallet> wallets = walletRepository.findByBankAccountAccountNumber(bankAccount.getAccountNumber());
                    BankAccountWithWalletResponseDTO response = new BankAccountWithWalletResponseDTO();
                    response.setId(idBankAccountCount);
                    response.setAccountNumber(bankAccount.getAccountNumber());
                    response.setType(bankAccount.getType());
                    response.setBalance(bankAccount.getBalance());
                    response.setListWallet(wallets.stream()
                            .map(wallet -> {
                                int idWalletCount = wallets.indexOf(wallet) + 1;
                                WalletWithCurrencyIconResponseDTO walletResponse = new WalletWithCurrencyIconResponseDTO();
                                walletResponse.setId(idWalletCount);
                                walletResponse.setWalletId(wallet.getId());
                                walletResponse.setCurrencyCode(wallet.getCurrency().getCode());
                                walletResponse.setCurrencyName(wallet.getCurrency().getName());
                                walletResponse.setBalance(wallet.getBalance());
                                walletResponse.setFlagIcon(wallet.getCurrency().getFlagIcon());
                                walletResponse.setLandmarkIcon(wallet.getCurrency().getLandmarkIcon());
                                return walletResponse;
                            }).collect(Collectors.toList()));
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public GetBankAccountWalletResponseDTO getBankAccountWallet(GetBankAccountWalletRequestDTO request, HttpServletRequest headerRequest) {

        // bank account request checking
        List<String> bankAccountNumbers = resourceRequestCheckerService.bankAccountChecker(headerRequest);
        if (!bankAccountNumbers.contains(request.getSenderAccountNumber())) {
            throw new UserException("Un-match request!");
        }

        if (request.getSenderAccountNumber().equals(request.getRecipientAccountNumber())) {
            throw new TransactionException("Transfers to the same account are not allowed!");
        }

        Wallet currentWallet = walletRepository.findByBankAccountAccountNumberAndCurrencyCode(request.getRecipientAccountNumber(), request.getCurrencyCode());

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