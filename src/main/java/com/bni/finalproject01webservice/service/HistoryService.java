package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.history.request.HistoryDataRequestDTO;
import com.bni.finalproject01webservice.dto.history.response.HistoryDataResponseDTO;
import com.bni.finalproject01webservice.interfaces.HistoryInterface;
import com.bni.finalproject01webservice.model.FinancialTrx;

import com.bni.finalproject01webservice.model.Withdrawal;
import com.bni.finalproject01webservice.repository.FinancialTrxRepository;

import com.bni.finalproject01webservice.repository.WithdrawalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HistoryService implements HistoryInterface {

    @Autowired
    private FinancialTrxRepository financialTrxRepository;

    @Autowired
    private WithdrawalRepository withdrawalRepository;

    @Override
    public List<HistoryDataResponseDTO> getHistoryData(HistoryDataRequestDTO request) {

        List<FinancialTrx> financialTrxs = financialTrxRepository.findByWalletId(request.getWalletId());
        List<Object[]> withdrawalTrxs = withdrawalRepository.findBySelectedWalletId(request.getWalletId());

        List<HistoryDataResponseDTO> historyDataResponse = new ArrayList<>();

        //ambil data di financial Transaction
        for (FinancialTrx financialTrx : financialTrxs) {
           if(financialTrx != null)
           {
               HistoryDataResponseDTO response = new HistoryDataResponseDTO();
               BeanUtils.copyProperties(financialTrx, response);

               response.setTrxId(financialTrx.getId());
               response.setAmount(financialTrx.getAmount());
               response.setCreatedDate(financialTrx.getCreatedAt());
               response.setTrxType(financialTrx.getTrxType().getName());
               response.setCurrencyCode(financialTrx.getWallet().getCurrency().getCode());

               historyDataResponse.add(response);
           }
        }

        //ambil data di withdrawal Transaction
        for (Object[] withdrawalTrx : withdrawalTrxs) {
            if (withdrawalTrx != null) {
                HistoryDataResponseDTO response = new HistoryDataResponseDTO();
                BeanUtils.copyProperties(withdrawalTrx, response);

                response.setTrxId((UUID) withdrawalTrx[0]);
                response.setAmount((BigDecimal) withdrawalTrx[1]);
                response.setCreatedDate((Date) withdrawalTrx[2]);
                response.setStatus((String) withdrawalTrx[3]);
                response.setCurrencyCode((String) withdrawalTrx[4]);
                response.setTrxType((String) withdrawalTrx[5]);

                historyDataResponse.add(response);
            }
        }
        return historyDataResponse;
    }
}
