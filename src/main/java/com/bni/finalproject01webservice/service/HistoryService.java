package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.history.request.HistoryDataRequestDTO;
import com.bni.finalproject01webservice.dto.history.response.HistoryDataResponseDTO;
import com.bni.finalproject01webservice.interfaces.HistoryInterface;
import com.bni.finalproject01webservice.model.FinancialTrx;
import com.bni.finalproject01webservice.model.WithdrawalTrx;
import com.bni.finalproject01webservice.repository.FinancialTrxRepository;
import com.bni.finalproject01webservice.repository.WithdrawalTrxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService implements HistoryInterface {

    @Autowired
    private FinancialTrxRepository financialTrxRepository;

    @Autowired
    private WithdrawalTrxRepository withdrawalTrxRepository;

    @Override
    public List<HistoryDataResponseDTO> getHistoryData(HistoryDataRequestDTO request) {

        List<FinancialTrx> financialTrxs = financialTrxRepository.findByWalletId(request.getWalletId());
        List<WithdrawalTrx> withdrawalTrxs = withdrawalTrxRepository.findByWalletId(request.getWalletId());

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
               response.setCurrencyCode(financialTrx.getWallet().getCurrency().getName());

               historyDataResponse.add(response);
           }
        }

        //ambil data di withdrawal Transaction
        for (WithdrawalTrx withdrawalTrx : withdrawalTrxs) {
            if (withdrawalTrx!= null) {
                HistoryDataResponseDTO response = new HistoryDataResponseDTO();
                BeanUtils.copyProperties(withdrawalTrx, response);

                response.setTrxId(withdrawalTrx.getId());
                response.setAmount(withdrawalTrx.getAmount());
                response.setCreatedDate(withdrawalTrx.getCreatedAt());
                response.setTrxType(withdrawalTrx.getTrxType().getName());
                response.setStatus(withdrawalTrx.getStatus());
                response.setCurrencyCode(withdrawalTrx.getWallet().getCurrency().getName());

                historyDataResponse.add(response);
            }
        }
        return historyDataResponse;
    }
}
