package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.withdraw_valas.request.DetailWithdrawValasRequestDTO;
import com.bni.finalproject01webservice.dto.withdraw_valas.request.WithdrawValasRequestDTO;
import com.bni.finalproject01webservice.dto.withdraw_valas.response.DetailWithdrawValasResponseDTO;
import com.bni.finalproject01webservice.dto.withdraw_valas.response.WithdrawValasCheckerResponseDTO;
import com.bni.finalproject01webservice.dto.withdraw_valas.response.WithdrawValasResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface WithdrawValasInterface {

    DetailWithdrawValasResponseDTO detailWithdrawValas(DetailWithdrawValasRequestDTO request);

    WithdrawValasResponseDTO withdrawValas(WithdrawValasRequestDTO request);

    void withdrawValasScheduledChecker();

    //////////////////////////////// VERSION 2.0 BLOCK ////////////////////////////////

    DetailWithdrawValasResponseDTO detailWithdrawValas(DetailWithdrawValasRequestDTO request, HttpServletRequest headerRequest);

    WithdrawValasResponseDTO withdrawValas(WithdrawValasRequestDTO request, HttpServletRequest headerRequest);

    WithdrawValasCheckerResponseDTO withdrawValasChecker(HttpServletRequest headerRequest);
}