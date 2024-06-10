package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.withdrawal_trx.request.DetailWithdrawalTrxRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal_trx.request.WithdrawalTrxRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal_trx.response.DetailWithdrawalTrxResponseDTO;
import com.bni.finalproject01webservice.dto.withdrawal_trx.response.WithdrawalTrxResponseDTO;

public interface WithdrawalTrxInterface {

    DetailWithdrawalTrxResponseDTO detailWithdrawalTrx(DetailWithdrawalTrxRequestDTO request);

    WithdrawalTrxResponseDTO withdrawalTrx(WithdrawalTrxRequestDTO request);
}
