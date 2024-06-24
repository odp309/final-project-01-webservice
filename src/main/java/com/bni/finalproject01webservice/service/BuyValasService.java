package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.configuration.exceptions.TransactionException;
import com.bni.finalproject01webservice.configuration.exceptions.UserException;
import com.bni.finalproject01webservice.configuration.exceptions.WalletException;
import com.bni.finalproject01webservice.dto.buy_valas.request.BuyValasRequestDTO;
import com.bni.finalproject01webservice.dto.buy_valas.request.DetailBuyValasRequestDTO;
import com.bni.finalproject01webservice.dto.buy_valas.request.LimitCheckRequestDTO;
import com.bni.finalproject01webservice.dto.buy_valas.response.BuyValasResponseDTO;
import com.bni.finalproject01webservice.dto.buy_valas.response.DetailBuyValasResponseDTO;
import com.bni.finalproject01webservice.dto.buy_valas.response.LimitCheckResponseDTO;
import com.bni.finalproject01webservice.dto.financial_trx.request.FinancialTrxRequestDTO;
import com.bni.finalproject01webservice.dto.financial_trx.response.FinancialTrxResponseDTO;
import com.bni.finalproject01webservice.interfaces.BuyValasInterface;
import com.bni.finalproject01webservice.interfaces.DateTimeInterface;
import com.bni.finalproject01webservice.interfaces.FinancialTrxInterface;
import com.bni.finalproject01webservice.interfaces.ResourceRequestCheckerInterface;
import com.bni.finalproject01webservice.model.*;
import com.bni.finalproject01webservice.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuyValasService implements BuyValasInterface {

    private final ExchangeRateRepository exchangeRateRepository;
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final UserLimitRepository userLimitRepository;

    private final PasswordEncoder passwordEncoder;
    private final FinancialTrxInterface financialTrxService;
    private final ResourceRequestCheckerInterface resourceRequestCheckerService;
    private final DateTimeInterface dateTimeService;

    @Override
    public DetailBuyValasResponseDTO detailBuyValas(DetailBuyValasRequestDTO request) {

        Wallet wallet = walletRepository.findById(request.getWalletId()).orElseThrow(() -> new WalletException("Wallet not found!"));
        UserLimit userLimit = userLimitRepository.findByUserId(wallet.getUser().getId());

        BigDecimal currLimit = userLimit.getLimitAccumulation();

        if (request.getAmountToBuy().compareTo(wallet.getCurrency().getMinimumBuy()) < 0) {
            throw new TransactionException("Amount is less than the minimum buy!");
        }

        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(wallet.getBankAccount().getAccountNumber());
        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRate(wallet.getCurrency().getCode());

        BigDecimal paidPrice = request.getAmountToBuy().multiply(exchangeRate.getBuyRate());

        if (paidPrice.compareTo(bankAccount.getBalance()) > 0) {
            throw new TransactionException("Balance insufficient!");
        }

        if (currLimit.compareTo(request.getAmountToBuy()) < 0) {
            throw new TransactionException("Buy limit has been reached!");
        }

        DetailBuyValasResponseDTO response = new DetailBuyValasResponseDTO();
        response.setCurrencyCode(exchangeRate.getCurrency().getCode());
        response.setCurrencyName(exchangeRate.getCurrency().getName());
        response.setBuyRate(exchangeRate.getBuyRate());
        response.setTotalAmountToBuy(request.getAmountToBuy().multiply(exchangeRate.getBuyRate()));

        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BuyValasResponseDTO buyValas(BuyValasRequestDTO request) {

        Wallet wallet = walletRepository.findById(request.getWalletId()).orElseThrow(() -> new WalletException("Wallet not found!"));
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(wallet.getBankAccount().getAccountNumber());
        User user = userRepository.findById(bankAccount.getUser().getId()).orElseThrow(() -> new UserException("User not found!"));
        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRate(wallet.getCurrency().getCode());
        ExchangeRate usdExchangeRate = exchangeRateRepository.findExchangeRate("USD");
        UserLimit userLimit = userLimitRepository.findByUserId(wallet.getUser().getId());

        if (request.getAmountToBuy().compareTo(wallet.getCurrency().getMinimumBuy()) < 0) {
            throw new TransactionException("Amount is less than the minimum buy!");
        }

        boolean checkPin = passwordEncoder.matches(request.getPin(), user.getPin());

        if (!checkPin) {
            throw new UserException("Invalid pin!");
        }

        BigDecimal currBalance = bankAccount.getBalance();
        BigDecimal paidPrice = request.getAmountToBuy().multiply(exchangeRate.getBuyRate());
        BigDecimal usdConversion;
        BigDecimal currLimit = userLimit.getLimitAccumulation();

        if (currLimit.compareTo(request.getAmountToBuy()) < 0) {
            throw new TransactionException("Buy limit has been reached!");
        }

        if (paidPrice.compareTo(currBalance) > 0) {
            throw new TransactionException("Balance insufficient!");
        }

        // wallet update balance (+)
        wallet.setBalance(wallet.getBalance().add(request.getAmountToBuy()));
        wallet.setUpdatedAt(dateTimeService.getCurrentDateTimeInJakarta());
        walletRepository.save(wallet);

        // bank account update balance (-)
        bankAccount.setBalance(currBalance.subtract(paidPrice));
        bankAccount.setUpdatedAt(dateTimeService.getCurrentDateTimeInJakarta());
        bankAccountRepository.save(bankAccount);

        // convert currency to usd currency
        if (wallet.getCurrency().getCode().equalsIgnoreCase("USD")) {
            usdConversion = request.getAmountToBuy();
        } else {
            BigDecimal usdAmount = usdExchangeRate.getBuyRate();
            int scale = 2;
            usdConversion = paidPrice.divide(usdAmount, scale, RoundingMode.HALF_UP);
        }

        // user limit update (-)
        userLimit.setLimitAccumulation(userLimit.getLimitAccumulation().subtract(usdConversion));
        userLimit.setUpdatedAt(dateTimeService.getCurrentDateTimeInJakarta());

        // create financial trx
        FinancialTrxRequestDTO financialTrxRequest = new FinancialTrxRequestDTO();
        financialTrxRequest.setUser(user);
        financialTrxRequest.setWallet(wallet);
        financialTrxRequest.setTrxTypeName("Beli");
        financialTrxRequest.setOperationTypeName("D");
        financialTrxRequest.setRate(exchangeRate.getBuyRate());
        financialTrxRequest.setAmount(request.getAmountToBuy());
        FinancialTrxResponseDTO financialTrxResponse = financialTrxService.addFinancialTrx(financialTrxRequest);

        BuyValasResponseDTO response = new BuyValasResponseDTO();
        response.setAmountToBuy(request.getAmountToBuy());
        response.setAmountToPay(paidPrice);
        response.setCurrencyCode(wallet.getCurrency().getCode());
        response.setCurrencyName(wallet.getCurrency().getName());
        response.setAccountNumber(wallet.getBankAccount().getAccountNumber());
        response.setCreatedAt(String.valueOf(dateTimeService.getCurrentDateTimeInJakarta()));

        return response;
    }

    @Override
    public LimitCheckResponseDTO getCurrentUserLimit(LimitCheckRequestDTO request) {

        UserLimit currLimit = userLimitRepository.findByUserId(request.getId());

        LimitCheckResponseDTO response = new LimitCheckResponseDTO();
        response.setCurrLimit(currLimit.getLimitAccumulation());

        return response;
    }

    //////////////////////////////// VERSION 2.0 BLOCK ////////////////////////////////

    @Override
    public DetailBuyValasResponseDTO detailBuyValas(DetailBuyValasRequestDTO request, HttpServletRequest headerRequest) {

        // wallet request checking
        List<UUID> walletIds = resourceRequestCheckerService.walletChecker(headerRequest);
        if (!walletIds.contains(request.getWalletId())) {
            throw new UserException("Un-match request!");
        }

        UUID userId = resourceRequestCheckerService.extractIdFromToken(headerRequest);

        Wallet wallet = walletRepository.findById(request.getWalletId()).orElseThrow(() -> new WalletException("Wallet not found!"));
        UserLimit userLimit = userLimitRepository.findByUserId(userId);

        BigDecimal currLimit = userLimit.getLimitAccumulation();

        if (request.getAmountToBuy().compareTo(wallet.getCurrency().getMinimumBuy()) < 0) {
            throw new TransactionException("Amount is less than the minimum buy!");
        }

        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(wallet.getBankAccount().getAccountNumber());
        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRate(wallet.getCurrency().getCode());

        BigDecimal paidPrice = request.getAmountToBuy().multiply(exchangeRate.getBuyRate());

        if (paidPrice.compareTo(bankAccount.getBalance()) > 0) {
            throw new TransactionException("Balance insufficient!");
        }

        if (currLimit.compareTo(request.getAmountToBuy()) < 0) {
            throw new TransactionException("Buy limit has been reached!");
        }

        DetailBuyValasResponseDTO response = new DetailBuyValasResponseDTO();
        response.setCurrencyCode(exchangeRate.getCurrency().getCode());
        response.setCurrencyName(exchangeRate.getCurrency().getName());
        response.setBuyRate(exchangeRate.getBuyRate());
        response.setTotalAmountToBuy(request.getAmountToBuy().multiply(exchangeRate.getBuyRate()));

        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BuyValasResponseDTO buyValas(BuyValasRequestDTO request, HttpServletRequest headerRequest) {

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
        ExchangeRate usdExchangeRate = exchangeRateRepository.findExchangeRate("USD");
        UserLimit userLimit = userLimitRepository.findByUserId(userId);

        if (request.getAmountToBuy().compareTo(wallet.getCurrency().getMinimumBuy()) < 0) {
            throw new TransactionException("Amount is less than the minimum buy!");
        }

        boolean checkPin = passwordEncoder.matches(request.getPin(), user.getPin());
        if (!checkPin) {
            throw new UserException("Invalid pin!");
        }

        BigDecimal currBalance = bankAccount.getBalance();
        BigDecimal paidPrice = request.getAmountToBuy().multiply(exchangeRate.getBuyRate());
        BigDecimal currLimit = userLimit.getLimitAccumulation();
        BigDecimal usdConversion;

        if (currLimit.compareTo(request.getAmountToBuy()) < 0) {
            throw new TransactionException("Buy limit has been reached!");
        }

        if (paidPrice.compareTo(currBalance) > 0) {
            throw new TransactionException("Balance insufficient!");
        }

        // wallet update balance (+)
        wallet.setBalance(wallet.getBalance().add(request.getAmountToBuy()));
        wallet.setUpdatedAt(dateTimeService.getCurrentDateTimeInJakarta());
        walletRepository.save(wallet);

        // bank account update balance (-)
        bankAccount.setBalance(currBalance.subtract(paidPrice));
        bankAccount.setUpdatedAt(dateTimeService.getCurrentDateTimeInJakarta());
        bankAccountRepository.save(bankAccount);

        // convert currency to usd currency
        if (wallet.getCurrency().getCode().equalsIgnoreCase("USD")) {
            usdConversion = request.getAmountToBuy();
        } else {
            BigDecimal usdAmount = usdExchangeRate.getBuyRate();
            int scale = 2;
            usdConversion = paidPrice.divide(usdAmount, scale, RoundingMode.HALF_UP);
        }

        // user limit update (-)
        userLimit.setLimitAccumulation(userLimit.getLimitAccumulation().subtract(usdConversion));
        userLimit.setUpdatedAt(dateTimeService.getCurrentDateTimeInJakarta());

        // create financial trx
        FinancialTrxRequestDTO financialTrxRequest = new FinancialTrxRequestDTO();
        financialTrxRequest.setUser(user);
        financialTrxRequest.setWallet(wallet);
        financialTrxRequest.setTrxTypeName("Beli");
        financialTrxRequest.setOperationTypeName("D");
        financialTrxRequest.setRate(exchangeRate.getBuyRate());
        financialTrxRequest.setAmount(request.getAmountToBuy());
        FinancialTrxResponseDTO financialTrxResponse = financialTrxService.addFinancialTrx(financialTrxRequest);

        BuyValasResponseDTO response = new BuyValasResponseDTO();
        response.setAmountToBuy(request.getAmountToBuy());
        response.setAmountToPay(paidPrice);
        response.setCurrencyCode(wallet.getCurrency().getCode());
        response.setCurrencyName(wallet.getCurrency().getName());
        response.setAccountNumber(wallet.getBankAccount().getAccountNumber());
        response.setCreatedAt(String.valueOf(dateTimeService.getCurrentDateTimeInJakarta()));

        return response;
    }

    @Override
    public LimitCheckResponseDTO getCurrentUserLimit(HttpServletRequest headerRequest) {

        UUID userId = resourceRequestCheckerService.extractIdFromToken(headerRequest);
        UserLimit currLimit = userLimitRepository.findByUserId(userId);

        LimitCheckResponseDTO response = new LimitCheckResponseDTO();
        response.setCurrLimit(currLimit.getLimitAccumulation());

        return response;
    }
}