package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.withdrawal_trx.response.ReservationTransactionValasRequestDTO;
import com.bni.finalproject01webservice.dto.financial_trx.request.TradeTransactionValasRequestDTO;
import com.bni.finalproject01webservice.dto.financial_trx.request.TransferTransactionValasRequestDTO;
import com.bni.finalproject01webservice.dto.financial_trx.response.TransactionValasResponseDTO;

public interface TransactionValasInterface {

    TransactionValasResponseDTO addTrfCreditTransactionValas(TransferTransactionValasRequestDTO request);

    TransactionValasResponseDTO addTrfDebitTransactionValas(TransferTransactionValasRequestDTO request);

    TransactionValasResponseDTO addBuyTransactionValas(TradeTransactionValasRequestDTO request);

    TransactionValasResponseDTO addSellTransactionValas(TradeTransactionValasRequestDTO request);

    TransactionValasResponseDTO addWithdrawTransactionValas(ReservationTransactionValasRequestDTO request);

    TransactionValasResponseDTO addRefundTransactionValas(ReservationTransactionValasRequestDTO request);
}
