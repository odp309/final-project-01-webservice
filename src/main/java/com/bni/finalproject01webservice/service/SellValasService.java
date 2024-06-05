package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.configuration.exceptions.TransactionException;
import com.bni.finalproject01webservice.configuration.exceptions.UserException;
import com.bni.finalproject01webservice.configuration.exceptions.WalletException;
import com.bni.finalproject01webservice.dto.sell_valas.request.DetailSellValasRequestDTO;
import com.bni.finalproject01webservice.dto.sell_valas.request.SellValasRequestDTO;
import com.bni.finalproject01webservice.dto.sell_valas.response.DetailSellValasResponseDTO;
import com.bni.finalproject01webservice.dto.sell_valas.response.SellValasResponseDTO;
import com.bni.finalproject01webservice.interfaces.SellValasInterface;
import com.bni.finalproject01webservice.model.BankAccount;
import com.bni.finalproject01webservice.model.ExchangeRate;
import com.bni.finalproject01webservice.model.User;
import com.bni.finalproject01webservice.model.Wallet;
import com.bni.finalproject01webservice.repository.BankAccountRepository;
import com.bni.finalproject01webservice.repository.ExchangeRateRepository;
import com.bni.finalproject01webservice.repository.UserRepository;
import com.bni.finalproject01webservice.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class SellValasService implements SellValasInterface {

    private final ExchangeRateRepository exchangeRateRepository;
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WalletRepository walletRepository;

    @Override
    public DetailSellValasResponseDTO detailSellValas(DetailSellValasRequestDTO request) {

        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRate(request.currencyCode);

        DetailSellValasResponseDTO response = new DetailSellValasResponseDTO();
        response.setCurrencyCode(exchangeRate.getCurrency().getCode());
        response.setCurrencyName(exchangeRate.getCurrency().getName());
        response.setSellRate(exchangeRate.getSellRate());
        response.setTotalAmountToSell(request.getAmountToSell().multiply(exchangeRate.getBuyRate()));

        return response;
    }

    @Override
    public SellValasResponseDTO sellValas(SellValasRequestDTO request) {

        SellValasResponseDTO response = new SellValasResponseDTO();

        Wallet wallet = walletRepository.findById(request.getWalletId()).orElseThrow(() -> new WalletException("Wallet not found!"));
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(wallet.getBankAccount().getAccountNumber());
        User user = userRepository.findById(bankAccount.getUser().getId()).orElseThrow(() -> new UserException("User not found!"));
        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRate(wallet.getCurrency().getCode());

        if (wallet.getCurrency().getMinimumDeposit().compareTo(BigDecimal.ONE) < 0) {
            throw new TransactionException("Amount is less than the minimum deposit!");
        }

        boolean checkPin = passwordEncoder.matches(request.getPin(), user.getPin());

        if (!checkPin) {
            throw new UserException("Invalid pin!");
        }

        BigDecimal currBalance = bankAccount.getBalance();
        BigDecimal paidPrice = request.getAmountToSell().multiply(exchangeRate.getSellRate());
        BigDecimal amountToSell = request.getAmountToSell();

        if (amountToSell.compareTo(wallet.getBalance()) > 0) {
            throw new TransactionException("Balance insufficient!");
        }

        wallet.setBalance(wallet.getBalance().subtract(request.getAmountToSell()));
        wallet.setUpdatedAt(new Date());
        walletRepository.save(wallet);

        bankAccount.setBalance(currBalance.subtract(paidPrice));
        bankAccount.setUpdatedAt(new Date());
        bankAccountRepository.save(bankAccount);

        response.setAmountToSell(request.getAmountToSell());
        response.setAmountToPay(paidPrice);
        response.setCurrencyCode(wallet.getCurrency().getCode());
        response.setCurrencyName(wallet.getCurrency().getName());
        response.setAccountNumber(wallet.getBankAccount().getAccountNumber());

        return response;
    }
}
