package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.trx_history.request.TrxHistoryRequestDTO;
import com.bni.finalproject01webservice.dto.trx_history.response.TrxHistoryResponseDTO;
import com.bni.finalproject01webservice.interfaces.TrxHistoryInterface;
import com.bni.finalproject01webservice.model.FinancialTrx;
import com.bni.finalproject01webservice.model.TrxHistory;
import com.bni.finalproject01webservice.model.WithdrawalTrx;
import com.bni.finalproject01webservice.repository.FinancialTrxRepository;
import com.bni.finalproject01webservice.repository.TrxHistoryRepository;
import com.bni.finalproject01webservice.repository.WithdrawalTrxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrxHistoryService implements TrxHistoryInterface {

    private final TrxHistoryRepository trxHistoryRepository;
    private final FinancialTrxRepository financialTrxRepository;
    private final WithdrawalTrxRepository withdrawalTrxRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TrxHistoryResponseDTO addTrxHistory(TrxHistoryRequestDTO request) {
        TrxHistory trxHistory = new TrxHistory();
        TrxHistoryResponseDTO response = new TrxHistoryResponseDTO();
        FinancialTrx financialTrx;
        WithdrawalTrx withdrawalTrx;

        if (request.getFinancialTrxId() != null) {
            financialTrx = financialTrxRepository.findById(request.getFinancialTrxId()).orElseThrow(() -> new RuntimeException("Financial trx id not found"));
            trxHistory.setFinancialTrx(financialTrx);
            response.setFinancialTrxId(request.getFinancialTrxId());
        } else {
            withdrawalTrx = withdrawalTrxRepository.findById(request.getWithdrawalTrxId()).orElseThrow(() -> new RuntimeException("Withdrawal trx id not found"));
            trxHistory.setWithdrawalTrx(withdrawalTrx);
            response.setWithdrawalTrxId(request.getWithdrawalTrxId());
        }
        trxHistory.setCreatedAt(new Date());
        trxHistoryRepository.save(trxHistory);

        return response;
    }
}
