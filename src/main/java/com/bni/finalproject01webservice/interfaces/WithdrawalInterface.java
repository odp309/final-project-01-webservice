package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.withdrawal.request.WithdrawalRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal.response.WithdrawalResponseDTO;
import com.bni.finalproject01webservice.dto.withdrawal.request.ReportWithdrawalRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal.response.ReportWithdrawalResponseDTO;

public interface WithdrawalInterface {

    WithdrawalResponseDTO addWithdrawal(WithdrawalRequestDTO request);

    ReportWithdrawalResponseDTO reportWithdrawal(ReportWithdrawalRequestDTO request);
}