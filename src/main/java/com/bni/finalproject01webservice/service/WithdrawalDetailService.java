package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.withdrawal_detail.request.WithdrawalDetailRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal_detail.response.WithdrawalDetailResponseDTO;
import com.bni.finalproject01webservice.interfaces.DateTimeInterface;
import com.bni.finalproject01webservice.interfaces.WithdrawalDetailInterface;
import com.bni.finalproject01webservice.model.OperationType;
import com.bni.finalproject01webservice.model.TrxType;
import com.bni.finalproject01webservice.model.Withdrawal;
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

    private final DateTimeInterface dateTimeService;

    @Override
    public WithdrawalDetailResponseDTO addWithdrawalDetail(WithdrawalDetailRequestDTO request) {

        TrxType trxType = trxTypeRepository.findByName(request.getTrxTypeName());
        OperationType operationType = operationTypeRepository.findByName(request.getOperationTypeName());
        Withdrawal withdrawal = request.getWithdrawal();

        WithdrawalDetail withdrawalDetail = new WithdrawalDetail();
        withdrawalDetail.setUser(withdrawal.getUser());
        withdrawalDetail.setWallet(withdrawal.getWallet());
        withdrawalDetail.setWithdrawal(request.getWithdrawal());
        withdrawalDetail.setDetail(
                request.getTrxTypeName() + "/" +
                withdrawal.getAmount().stripTrailingZeros().toPlainString() + " " + withdrawal.getWallet().getCurrency().getCode() + "/" +
                withdrawal.getBranch().getType().split("/")[1] + " " + withdrawal.getBranch().getName() + "/" +
                withdrawal.getReservationCode());
        withdrawalDetail.setTrxType(trxType);
        withdrawalDetail.setOperationType(operationType);
        withdrawalDetail.setCreatedAt(dateTimeService.getCurrentDateTimeInJakarta());
        withdrawalDetailRepository.save(withdrawalDetail);

        WithdrawalDetailResponseDTO response = new WithdrawalDetailResponseDTO();
        response.setWithdrawalDetail(withdrawalDetail.getId());
        response.setDetail(withdrawalDetail.getDetail());

        return response;
    }
}
