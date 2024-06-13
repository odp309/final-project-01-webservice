package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.configuration.exceptions.UserException;
import com.bni.finalproject01webservice.dto.history.request.HistoryDataRequestDTO;
import com.bni.finalproject01webservice.dto.history.request.HistoryDetailRequestDTO;
import com.bni.finalproject01webservice.dto.history.response.HistoryDataResponseDTO;
import com.bni.finalproject01webservice.dto.history.response.HistoryDetailResponseDTO;
import com.bni.finalproject01webservice.interfaces.HistoryInterface;
import com.bni.finalproject01webservice.model.FinancialTrx;

import com.bni.finalproject01webservice.model.Withdrawal;
import com.bni.finalproject01webservice.model.WithdrawalDetail;
import com.bni.finalproject01webservice.repository.FinancialTrxRepository;

import com.bni.finalproject01webservice.repository.WithdrawalDetailRepository;
import com.bni.finalproject01webservice.repository.WithdrawalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class HistoryService implements HistoryInterface {

    @Autowired
    private FinancialTrxRepository financialTrxRepository;
    @Autowired
    private WithdrawalRepository withdrawalRepository;
    @Autowired
    private WithdrawalDetailRepository withdrawalDetailRepository;

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

    @Override
    public HistoryDetailResponseDTO getHistoryDetail(HistoryDetailRequestDTO request) {
        Optional<FinancialTrx> optionalFinancialTrx = financialTrxRepository.findById(request.getTrxId());

        HistoryDetailResponseDTO response = new HistoryDetailResponseDTO();

        if (optionalFinancialTrx.isPresent())
        {
            FinancialTrx financialTrxs = optionalFinancialTrx.get();
            String[] cekDetail = (financialTrxs.getDetail()).split("/");
            String firstWord = cekDetail[0].toLowerCase();


            response.setTrxId(financialTrxs.getId());
            response.setAmount(financialTrxs.getAmount());
            response.setCreatedDate(financialTrxs.getCreatedAt());
            response.setDetail(financialTrxs.getDetail());
            response.setOperationType(financialTrxs.getOperationType().getName());
            response.setTrxType(financialTrxs.getTrxType().getName());
            response.setCurrencyCode(financialTrxs.getWallet().getCurrency().getCode());

            switch (firstWord){
                case "beli":
                case "jual":
                    BigDecimal kurs = new BigDecimal(cekDetail[2]);
                    response.setKurs(kurs);
                    response.setPaidPrice(financialTrxs.getAmount().multiply(kurs));
                    break;
                case "transfer":
                    response.setAccountName(cekDetail[2]);
                    break;
            }

        }
        else
        {
            WithdrawalDetail withdrawalTrxs = withdrawalDetailRepository.findById(request.getTrxId()).orElseThrow(() -> new RuntimeException("Trx Id Not found"));

            String[] cekDetail = (withdrawalTrxs.getDetail()).split("/");
            String firstWord = cekDetail[0].toLowerCase();

            response.setTrxId(withdrawalTrxs.getId());
            response.setAmount(withdrawalTrxs.getWithdrawal().getAmount());
            response.setCreatedDate(withdrawalTrxs.getCreatedAt());
            response.setReservationDate(withdrawalTrxs.getWithdrawal().getReservationDate());
            response.setDetail(withdrawalTrxs.getDetail());
            response.setOperationType(withdrawalTrxs.getOperationType().getName());
            response.setTrxType(withdrawalTrxs.getTrxType().getName());
            response.setCurrencyCode(withdrawalTrxs.getWallet().getCurrency().getCode());

            switch (firstWord){
                case "tarik":
                    response.setReservationCode(withdrawalTrxs.getWithdrawal().getReservationCode());
                    break;
                case "refund":
                    break;
            }

        }

        return response;
    }


}
