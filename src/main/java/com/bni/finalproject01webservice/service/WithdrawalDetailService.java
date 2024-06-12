package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.withdrawal_detail.request.WithdrawalDetailRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal_detail.response.WithdrawalDetailResponseDTO;
import com.bni.finalproject01webservice.interfaces.WithdrawalDetailInterface;
import com.bni.finalproject01webservice.model.WithdrawalDetail;
import com.bni.finalproject01webservice.repository.WithdrawalDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WithdrawalDetailService implements WithdrawalDetailInterface {

    private final WithdrawalDetailRepository withdrawalDetailRepository;

    @Override
    public WithdrawalDetailResponseDTO withdrawalDetail(WithdrawalDetailRequestDTO request) {

        WithdrawalDetail withdrawalDetail = new WithdrawalDetail();


        return null;
    }
}
