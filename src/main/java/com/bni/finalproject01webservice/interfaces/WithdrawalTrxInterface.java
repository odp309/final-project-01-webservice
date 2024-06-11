package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.withdrawal_trx.request.WithdrawalTrxRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal_trx.response.WithdrawalTrxResponseDTO;

public interface WithdrawalTrxInterface {

    WithdrawalTrxResponseDTO addWithdrawalTrx(WithdrawalTrxRequestDTO request);
}