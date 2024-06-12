package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.withdrawal_detail.request.WithdrawalDetailRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal_detail.response.WithdrawalDetailResponseDTO;

public interface WithdrawalDetailInterface {

    WithdrawalDetailResponseDTO addWithdrawalDetail(WithdrawalDetailRequestDTO request);
}
