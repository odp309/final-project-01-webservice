package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.configuration.exceptions.WalletException;
import com.bni.finalproject01webservice.dto.withdrawal_trx.response.ReservationTransactionValasRequestDTO;
import com.bni.finalproject01webservice.dto.financial_trx.request.TradeTransactionValasRequestDTO;
import com.bni.finalproject01webservice.dto.financial_trx.request.TransferTransactionValasRequestDTO;
import com.bni.finalproject01webservice.dto.financial_trx.response.TransactionValasResponseDTO;
import com.bni.finalproject01webservice.interfaces.TransactionValasInterface;
import com.bni.finalproject01webservice.model.TransactionValas;
import com.bni.finalproject01webservice.model.Wallet;
import com.bni.finalproject01webservice.repository.ExchangeRateRepository;
import com.bni.finalproject01webservice.repository.TransactionValasRepository;
import com.bni.finalproject01webservice.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TransactionValasService implements TransactionValasInterface {

    private final TransactionValasRepository transactionValasRepository;
    private final ExchangeRateRepository exchangeRateRepository;
    private final WalletRepository walletRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransactionValasResponseDTO addTrfCreditTransactionValas(TransferTransactionValasRequestDTO request) {
        Wallet wallet = walletRepository.findById(request.getWalletId()).orElseThrow(() -> new WalletException("Wallet not found!"));

        // 1st save to get UUID
        TransactionValas transactionValas = new TransactionValas();
        transactionValas.setAmount(request.getAmount());
        transactionValas.setType("Transfer");
        transactionValas.setOperation("K");
        transactionValas.setDetail("");
        transactionValas.setWallet(wallet);
        transactionValas.setUser(wallet.getUser());
        transactionValas.setCreatedAt(new Date());
        transactionValasRepository.save(transactionValas);

        // 2nd save to insert UUID into detail
        // Example: Transfer/10 USD/Bagas Pradipabista/49662eb3-360f-4bb4-af61-2243e3b9b8d8
        // Explanation: [trx type]/[currency amount *space* currency code]/[full name]/
        transactionValas.setDetail("Transfer/"
                + request.getAmount() + " " + wallet.getCurrency().getCode() + "/"
                + request.getFullName() + "/"
                + transactionValas.getId());
        transactionValasRepository.save(transactionValas);

//        TransactionValasResponseDTO response = new TransactionValasResponseDTO();
//        response.setAmount(request.getAmount());
//        response.setType("K");
//        response.setDescription();

        return null;
    }

    @Override
    public TransactionValasResponseDTO addTrfDebitTransactionValas(TransferTransactionValasRequestDTO request) {

        return null;
    }

    @Override
    public TransactionValasResponseDTO addBuyTransactionValas(TradeTransactionValasRequestDTO request) {
        return null;
    }

    @Override
    public TransactionValasResponseDTO addSellTransactionValas(TradeTransactionValasRequestDTO request) {
        return null;
    }

    @Override
    public TransactionValasResponseDTO addWithdrawTransactionValas(ReservationTransactionValasRequestDTO request) {
        return null;
    }

    @Override
    public TransactionValasResponseDTO addRefundTransactionValas(ReservationTransactionValasRequestDTO request) {
        return null;
    }
}
