package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.configuration.exceptions.TransactionException;
import com.bni.finalproject01webservice.configuration.exceptions.UserException;
import com.bni.finalproject01webservice.configuration.exceptions.WalletException;
import com.bni.finalproject01webservice.dto.financial_trx.request.FinancialTrxRequestDTO;
import com.bni.finalproject01webservice.dto.financial_trx.response.FinancialTrxResponseDTO;
import com.bni.finalproject01webservice.dto.sell_valas.request.DetailSellValasRequestDTO;
import com.bni.finalproject01webservice.dto.sell_valas.request.SellValasRequestDTO;
import com.bni.finalproject01webservice.dto.sell_valas.response.DetailSellValasResponseDTO;
import com.bni.finalproject01webservice.dto.sell_valas.response.SellValasResponseDTO;
import com.bni.finalproject01webservice.interfaces.DateTimeInterface;
import com.bni.finalproject01webservice.interfaces.FinancialTrxInterface;
import com.bni.finalproject01webservice.interfaces.ResourceRequestCheckerInterface;
import com.bni.finalproject01webservice.interfaces.SellValasInterface;
import com.bni.finalproject01webservice.model.BankAccount;
import com.bni.finalproject01webservice.model.ExchangeRate;
import com.bni.finalproject01webservice.model.User;
import com.bni.finalproject01webservice.model.Wallet;
import com.bni.finalproject01webservice.repository.BankAccountRepository;
import com.bni.finalproject01webservice.repository.ExchangeRateRepository;
import com.bni.finalproject01webservice.repository.UserRepository;
import com.bni.finalproject01webservice.repository.WalletRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SellValasService implements SellValasInterface {

    private final ExchangeRateRepository exchangeRateRepository;
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    private final PasswordEncoder passwordEncoder;
    private final FinancialTrxInterface financialTrxService;
    private final ResourceRequestCheckerInterface resourceRequestCheckerService;
    private final DateTimeInterface dateTimeService;

    @Override
    public DetailSellValasResponseDTO detailSellValas(DetailSellValasRequestDTO request) {

        Wallet wallet = walletRepository.findById(request.getWalletId()).orElseThrow(() -> new WalletException("Wallet not found!"));

        if (request.getAmountToSell().compareTo(wallet.getCurrency().getMinimumSell()) < 0) {
            throw new TransactionException("Amount is less than the minimum sell!");
        }

        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRate(wallet.getCurrency().getCode());

        if (request.getAmountToSell().compareTo(wallet.getBalance()) > 0) {
            throw new TransactionException("Wallet balance insufficient!");
        }

        DetailSellValasResponseDTO response = new DetailSellValasResponseDTO();
        response.setCurrencyCode(exchangeRate.getCurrency().getCode());
        response.setCurrencyName(exchangeRate.getCurrency().getName());
        response.setSellRate(exchangeRate.getSellRate());
        response.setTotalAmountToSell(request.getAmountToSell().multiply(exchangeRate.getSellRate()));

        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SellValasResponseDTO sellValas(SellValasRequestDTO request) {

        Wallet wallet = walletRepository.findById(request.getWalletId()).orElseThrow(() -> new WalletException("Wallet not found!"));
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(wallet.getBankAccount().getAccountNumber());
        User user = userRepository.findById(bankAccount.getUser().getId()).orElseThrow(() -> new UserException("User not found!"));
        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRate(wallet.getCurrency().getCode());

        if (request.getAmountToSell().compareTo(wallet.getCurrency().getMinimumSell()) < 0) {
            throw new TransactionException("Amount is less than the minimum sell!");
        }

        boolean checkPin = passwordEncoder.matches(request.getPin(), user.getPin());

        if (!checkPin) {
            throw new UserException("Invalid pin!");
        }

        BigDecimal currBalance = bankAccount.getBalance();
        BigDecimal sellPrice = request.getAmountToSell().multiply(exchangeRate.getSellRate());

        if (request.getAmountToSell().compareTo(wallet.getBalance()) > 0) {
            throw new TransactionException("Wallet balance insufficient!");
        }

        // wallet update balance (-)
        wallet.setBalance(wallet.getBalance().subtract(request.getAmountToSell()));
        wallet.setUpdatedAt(dateTimeService.getCurrentDateTimeInJakarta());
        walletRepository.save(wallet);

        // bank account update balance (+)
        bankAccount.setBalance(currBalance.add(sellPrice));
        bankAccount.setUpdatedAt(dateTimeService.getCurrentDateTimeInJakarta());
        bankAccountRepository.save(bankAccount);

        // create financial trx
        FinancialTrxRequestDTO financialTrxRequest = new FinancialTrxRequestDTO();
        financialTrxRequest.setUser(user);
        financialTrxRequest.setWallet(wallet);
        financialTrxRequest.setTrxTypeName("Jual");
        financialTrxRequest.setOperationTypeName("K");
        financialTrxRequest.setRate(exchangeRate.getSellRate());
        financialTrxRequest.setAmount(request.getAmountToSell());
        FinancialTrxResponseDTO financialTrxResponse = financialTrxService.addFinancialTrx(financialTrxRequest);

        SellValasResponseDTO response = new SellValasResponseDTO();
        response.setAmountToSell(request.getAmountToSell());
        response.setAmountToReceive(sellPrice);
        response.setCurrencyCode(wallet.getCurrency().getCode());
        response.setCurrencyName(wallet.getCurrency().getName());
        response.setAccountNumber(wallet.getBankAccount().getAccountNumber());
        response.setCreatedAt(String.valueOf(dateTimeService.getCurrentDateTimeInJakarta()));

        return response;
    }

    //////////////////////////////// VERSION 2.0 BLOCK ////////////////////////////////

    @Override
    public DetailSellValasResponseDTO detailSellValas(DetailSellValasRequestDTO request, HttpServletRequest headerRequest) {

        // wallet request checking
        List<UUID> walletIds = resourceRequestCheckerService.walletChecker(headerRequest);
        if (!walletIds.contains(request.getWalletId())) {
            throw new UserException("Un-match request!");
        }

        Wallet wallet = walletRepository.findById(request.getWalletId()).orElseThrow(() -> new WalletException("Wallet not found!"));

        if (request.getAmountToSell().compareTo(wallet.getCurrency().getMinimumSell()) < 0) {
            throw new TransactionException("Amount is less than the minimum sell!");
        }

        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRate(wallet.getCurrency().getCode());

        if (request.getAmountToSell().compareTo(wallet.getBalance()) > 0) {
            throw new TransactionException("Wallet balance insufficient!");
        }

        DetailSellValasResponseDTO response = new DetailSellValasResponseDTO();
        response.setCurrencyCode(exchangeRate.getCurrency().getCode());
        response.setCurrencyName(exchangeRate.getCurrency().getName());
        response.setSellRate(exchangeRate.getSellRate());
        response.setTotalAmountToSell(request.getAmountToSell().multiply(exchangeRate.getSellRate()));

        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SellValasResponseDTO sellValas(SellValasRequestDTO request, HttpServletRequest headerRequest) {

        // wallet request checking
        List<UUID> walletIds = resourceRequestCheckerService.walletChecker(headerRequest);
        if (!walletIds.contains(request.getWalletId())) {
            throw new UserException("Un-match request!");
        }

        UUID userId = resourceRequestCheckerService.extractIdFromToken(headerRequest);

        Wallet wallet = walletRepository.findById(request.getWalletId()).orElseThrow(() -> new WalletException("Wallet not found!"));
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(wallet.getBankAccount().getAccountNumber());
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found!"));
        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRate(wallet.getCurrency().getCode());

        if (request.getAmountToSell().compareTo(wallet.getCurrency().getMinimumSell()) < 0) {
            throw new TransactionException("Amount is less than the minimum sell!");
        }

        boolean checkPin = passwordEncoder.matches(request.getPin(), user.getPin());

        if (!checkPin) {
            throw new UserException("Invalid pin!");
        }

        BigDecimal currBalance = bankAccount.getBalance();
        BigDecimal sellPrice = request.getAmountToSell().multiply(exchangeRate.getSellRate());

        if (request.getAmountToSell().compareTo(wallet.getBalance()) > 0) {
            throw new TransactionException("Wallet balance insufficient!");
        }

        // wallet update balance (-)
        wallet.setBalance(wallet.getBalance().subtract(request.getAmountToSell()));
        wallet.setUpdatedAt(dateTimeService.getCurrentDateTimeInJakarta());
        walletRepository.save(wallet);

        // bank account update balance (+)
        bankAccount.setBalance(currBalance.add(sellPrice));
        bankAccount.setUpdatedAt(dateTimeService.getCurrentDateTimeInJakarta());
        bankAccountRepository.save(bankAccount);

        // create financial trx
        FinancialTrxRequestDTO financialTrxRequest = new FinancialTrxRequestDTO();
        financialTrxRequest.setUser(user);
        financialTrxRequest.setWallet(wallet);
        financialTrxRequest.setTrxTypeName("Jual");
        financialTrxRequest.setOperationTypeName("K");
        financialTrxRequest.setRate(exchangeRate.getSellRate());
        financialTrxRequest.setAmount(request.getAmountToSell());
        FinancialTrxResponseDTO financialTrxResponse = financialTrxService.addFinancialTrx(financialTrxRequest);

        SellValasResponseDTO response = new SellValasResponseDTO();
        response.setAmountToSell(request.getAmountToSell());
        response.setAmountToReceive(sellPrice);
        response.setCurrencyCode(wallet.getCurrency().getCode());
        response.setCurrencyName(wallet.getCurrency().getName());
        response.setAccountNumber(wallet.getBankAccount().getAccountNumber());
        response.setCreatedAt(String.valueOf(dateTimeService.getCurrentDateTimeInJakarta()));

        return response;
    }
}
