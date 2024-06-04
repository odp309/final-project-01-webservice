package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.configuration.exceptions.TransactionException;
import com.bni.finalproject01webservice.configuration.exceptions.UserException;
import com.bni.finalproject01webservice.configuration.exceptions.WalletException;
import com.bni.finalproject01webservice.dto.request.BuyValasRequestDTO;
import com.bni.finalproject01webservice.dto.request.DetailBuyValasRequestDTO;
import com.bni.finalproject01webservice.dto.response.BuyValasResponseDTO;
import com.bni.finalproject01webservice.dto.response.DetailBuyValasResponseDTO;
import com.bni.finalproject01webservice.interfaces.BuyValasInterface;
import com.bni.finalproject01webservice.model.BankAccount;
import com.bni.finalproject01webservice.model.ExchangeRate;
import com.bni.finalproject01webservice.model.User;
import com.bni.finalproject01webservice.model.Wallet;
import com.bni.finalproject01webservice.repository.BankAccountRepository;
import com.bni.finalproject01webservice.repository.ExchangeRateRepository;
import com.bni.finalproject01webservice.repository.UserRepository;
import com.bni.finalproject01webservice.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class BuyValasService implements BuyValasInterface {

    private final ExchangeRateRepository exchangeRateRepository;
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WalletRepository walletRepository;

    @Override
    public DetailBuyValasResponseDTO detailBuyValas(DetailBuyValasRequestDTO request) {
        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRate(request.currencyCode);

        DetailBuyValasResponseDTO response = new DetailBuyValasResponseDTO();
        response.setCurrencyCode(exchangeRate.getCurrency().getCode());
        response.setCurrencyName(exchangeRate.getCurrency().getName());
        response.setTotalAmountToBuy(request.getAmountToBuy().multiply(exchangeRate.getBuyRate()));
        response.setBuyRate(exchangeRate.getBuyRate());

        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BuyValasResponseDTO buyValas(BuyValasRequestDTO request) {
        BuyValasResponseDTO response = new BuyValasResponseDTO();

        Wallet wallet = walletRepository.findById(request.getWalletId()).orElseThrow(() -> new WalletException("Wallet not found!"));
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(wallet.getBankAccount().getAccountNumber());
        User user = userRepository.findById(bankAccount.getUser().getId()).orElseThrow(() -> new UserException("User not found!"));
        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRate(wallet.getCurrency().getCode());

        boolean checkPin = passwordEncoder.matches(request.getPin(), user.getPin());

        if (!checkPin) {
            throw new UserException("Invalid pin!");
        }

        BigDecimal currBalance = bankAccount.getBalance();
        BigDecimal paidPrice = request.getAmountToBuy().multiply(exchangeRate.getBuyRate());

        if (paidPrice.compareTo(currBalance) > 0) {
            throw new TransactionException("Balance insufficient!");
        }

        wallet.setBalance(wallet.getBalance().add(request.getAmountToBuy()));
        wallet.setUpdatedAt(new Date());
        walletRepository.save(wallet);

        bankAccount.setBalance(currBalance.subtract(paidPrice));
        bankAccount.setUpdatedAt(new Date());
        bankAccountRepository.save(bankAccount);

        response.setAmountToBuy(request.getAmountToBuy());
        response.setAmountToPay(request.getAmountToBuy().multiply(exchangeRate.getBuyRate()));
        response.setCurrencyCode(wallet.getCurrency().getCode());
        response.setCurrencyName(wallet.getCurrency().getName());
        response.setAccountNumber(wallet.getBankAccount().getAccountNumber());

        return response;
    }
}
