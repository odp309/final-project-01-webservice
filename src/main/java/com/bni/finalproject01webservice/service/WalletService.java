package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.configuration.exceptions.UserException;
import com.bni.finalproject01webservice.configuration.exceptions.WalletException;
import com.bni.finalproject01webservice.dto.request.AddWalletRequestDTO;
import com.bni.finalproject01webservice.dto.response.GetAllWalletResponseDTO;
import com.bni.finalproject01webservice.dto.response.WalletResponseDTO;
import com.bni.finalproject01webservice.interfaces.WalletInterface;
import com.bni.finalproject01webservice.model.BankAccount;
import com.bni.finalproject01webservice.model.Currency;
import com.bni.finalproject01webservice.model.User;
import com.bni.finalproject01webservice.model.Wallet;
import com.bni.finalproject01webservice.repository.BankAccountRepository;
import com.bni.finalproject01webservice.repository.CurrencyRepository;
import com.bni.finalproject01webservice.repository.UserRepository;
import com.bni.finalproject01webservice.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletService implements WalletInterface {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    private final BankAccountRepository bankAccountRepository;

    @Override
    public GetAllWalletResponseDTO getWallet(String accountNumber) {
        List<Wallet> wallets = walletRepository.findByBankAccountAccountNumber(accountNumber);
        if (wallets == null || wallets.isEmpty()) {
            throw new RuntimeException("wallet not found");
        }

        Wallet firstWallet = wallets.get(0);
        GetAllWalletResponseDTO response = new GetAllWalletResponseDTO();
        response.setAccountNumber(firstWallet.getBankAccount().getAccountNumber());
        //response.setId_user(firstWallet.getUser().get);

        List<GetAllWalletResponseDTO.WalletInfo> walletInfos = wallets.stream()
                .map(wallet -> {
                    GetAllWalletResponseDTO.WalletInfo walletInfo = new GetAllWalletResponseDTO.WalletInfo();
                    walletInfo.setCurrencyId(wallet.getCurrency().getCode());
                    walletInfo.setBalance(wallet.getBalance().floatValue());
                    return walletInfo;
                })
                .collect(Collectors.toList());

        response.setWallet(walletInfos);

        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WalletResponseDTO addWallet(AddWalletRequestDTO request) {
        Wallet currentWallet = walletRepository.findByBankAccountAccountNumberAndCurrencyCode(request.getAccountNumber(), request.getCurrencyCode());

        if (currentWallet != null) {
            throw new WalletException("Wallet already exist!");
        }

        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new UserException("User not found!"));
        Currency currency = currencyRepository.findByCode(request.getCurrencyCode());
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(request.getAccountNumber());

        Wallet newWallet = new Wallet();
        newWallet.setBalance(request.getTotalAmount());
        newWallet.setCreatedAt(new Date());
        newWallet.setBankAccount(bankAccount);
        newWallet.setUser(user);
        newWallet.setCurrency(currency);
        walletRepository.save(newWallet);

        WalletResponseDTO response = new WalletResponseDTO();
        response.setBalance(request.getTotalAmount());
        response.setAccountNumber(bankAccount.getAccountNumber());
        response.setUserId(user.getId());
        response.setCurrencyCode(currency.getCode());

        return response;
    }
}
