package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.configuration.exceptions.TransactionException;
import com.bni.finalproject01webservice.configuration.exceptions.UserException;
import com.bni.finalproject01webservice.configuration.exceptions.WalletException;
import com.bni.finalproject01webservice.dto.buy_valas.request.BuyValasRequestDTO;
import com.bni.finalproject01webservice.dto.buy_valas.request.DetailBuyValasRequestDTO;
import com.bni.finalproject01webservice.dto.buy_valas.response.BuyValasResponseDTO;
import com.bni.finalproject01webservice.dto.buy_valas.response.DetailBuyValasResponseDTO;
import com.bni.finalproject01webservice.dto.financial_trx.request.FinancialTrxRequestDTO;
import com.bni.finalproject01webservice.dto.financial_trx.response.FinancialTrxResponseDTO;
import com.bni.finalproject01webservice.dto.trx_history.request.TrxHistoryRequestDTO;
import com.bni.finalproject01webservice.dto.trx_history.response.TrxHistoryResponseDTO;
import com.bni.finalproject01webservice.interfaces.BuyValasInterface;
import com.bni.finalproject01webservice.interfaces.FinancialTrxInterface;
import com.bni.finalproject01webservice.interfaces.TrxHistoryInterface;
import com.bni.finalproject01webservice.model.BankAccount;
import com.bni.finalproject01webservice.model.ExchangeRate;
import com.bni.finalproject01webservice.model.User;
import com.bni.finalproject01webservice.model.Wallet;
import com.bni.finalproject01webservice.repository.*;
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

    private final FinancialTrxInterface financialTrxService;
    private final TrxHistoryInterface trxHistoryService;

    @Override
    public DetailBuyValasResponseDTO detailBuyValas(DetailBuyValasRequestDTO request) {
        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRate(request.currencyCode);

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

        if (request.getAmountToBuy().compareTo(wallet.getCurrency().getMinimumBuy()) < 0) {
            throw new TransactionException("Amount is less than the minimum buy!");
        }

        boolean checkPin = passwordEncoder.matches(request.getPin(), user.getPin());

        if (!checkPin) {
            throw new UserException("Invalid pin!");
        }

        BigDecimal currBalance = bankAccount.getBalance();
        BigDecimal paidPrice = request.getAmountToBuy().multiply(exchangeRate.getBuyRate());

        if (paidPrice.compareTo(currBalance) > 0) {
            throw new TransactionException("Balance insufficient!");
        }

        // wallet update balance (+)
        wallet.setBalance(wallet.getBalance().add(request.getAmountToBuy()));
        wallet.setUpdatedAt(new Date());
        walletRepository.save(wallet);

        // bank account update balance (-)
        bankAccount.setBalance(currBalance.subtract(paidPrice));
        bankAccount.setUpdatedAt(new Date());
        bankAccountRepository.save(bankAccount);

        // create financial trx
        FinancialTrxRequestDTO financialTrxRequest = new FinancialTrxRequestDTO();
        financialTrxRequest.setUser(user);
        financialTrxRequest.setWallet(wallet);
        financialTrxRequest.setTrxTypeName("Beli");
        financialTrxRequest.setOperationTypeName("D");
        financialTrxRequest.setRate(exchangeRate.getBuyRate());
        financialTrxRequest.setAmount(request.getAmountToBuy());
        FinancialTrxResponseDTO financialTrxResponse = financialTrxService.addFinancialTrx(financialTrxRequest);

        // create history trx
        TrxHistoryRequestDTO trxHistoryRequest = new TrxHistoryRequestDTO();
        trxHistoryRequest.setFinancialTrxId(financialTrxResponse.getFinancialTrxId());
        TrxHistoryResponseDTO trxHistoryResponse = trxHistoryService.addTrxHistory(trxHistoryRequest);

        BuyValasResponseDTO response = new BuyValasResponseDTO();
        response.setAmountToBuy(request.getAmountToBuy());
        response.setAmountToPay(paidPrice);
        response.setCurrencyCode(wallet.getCurrency().getCode());
        response.setCurrencyName(wallet.getCurrency().getName());
        response.setAccountNumber(wallet.getBankAccount().getAccountNumber());
        response.setTrxHistory(trxHistoryResponse);

        return response;
    }
}
