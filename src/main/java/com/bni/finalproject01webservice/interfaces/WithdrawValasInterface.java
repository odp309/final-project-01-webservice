package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.withdraw_valas.request.DetailWithdrawValasRequestDTO;
import com.bni.finalproject01webservice.dto.withdraw_valas.request.WithdrawValasRequestDTO;
import com.bni.finalproject01webservice.dto.withdraw_valas.response.DetailWithdrawValasResponseDTO;
import com.bni.finalproject01webservice.dto.withdraw_valas.response.WithdrawValasResponseDTO;

public interface WithdrawValasInterface {

    DetailWithdrawValasResponseDTO detailWithdrawValas(DetailWithdrawValasRequestDTO request);

    WithdrawValasResponseDTO withdrawValas(WithdrawValasRequestDTO request);

    void withdrawValasChecker();
}