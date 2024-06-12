package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.withdrawal_detail.request.WithdrawalDetailRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal_detail.response.WithdrawalDetailResponseDTO;
import com.bni.finalproject01webservice.interfaces.WithdrawalDetailInterface;
import com.bni.finalproject01webservice.model.OperationType;
import com.bni.finalproject01webservice.model.TrxType;
import com.bni.finalproject01webservice.model.WithdrawalDetail;
import com.bni.finalproject01webservice.repository.OperationTypeRepository;
import com.bni.finalproject01webservice.repository.TrxTypeRepository;
import com.bni.finalproject01webservice.repository.WithdrawalDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class WithdrawalDetailService implements WithdrawalDetailInterface {

    private final WithdrawalDetailRepository withdrawalDetailRepository;
    private final TrxTypeRepository trxTypeRepository;
    private final OperationTypeRepository operationTypeRepository;

    @Override
    public WithdrawalDetailResponseDTO withdrawalDetail(WithdrawalDetailRequestDTO request) {

        TrxType trxType = trxTypeRepository.findByName(request.getTrxTypeName());
        OperationType operationType = operationTypeRepository.findByName(request.getOperationTypeName());

        WithdrawalDetail withdrawalDetail = new WithdrawalDetail();
        withdrawalDetail.setDetail(request.getDetail());
        withdrawalDetail.setCreatedAt(new Date());
        withdrawalDetail.setUser(request.getUser());
        withdrawalDetail.setWallet(request.getWallet());
        withdrawalDetail.setWithdrawal(request.getWithdrawal());
        withdrawalDetail.setTrxType(trxType);
        withdrawalDetail.setOperationType(operationType);
        withdrawalDetailRepository.save(withdrawalDetail);

        return null;
    }
}
