package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.withdrawal.request.WithdrawalRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal.response.WithdrawalResponseDTO;

public interface WithdrawalInterface {

    WithdrawalResponseDTO addWithdrawal(WithdrawalRequestDTO request);
}