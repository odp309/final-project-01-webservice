package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.trx_history.request.TrxHistoryRequestDTO;
import com.bni.finalproject01webservice.dto.trx_history.response.TrxHistoryResponseDTO;

public interface TrxHistoryInterface {

    TrxHistoryResponseDTO addTrxHistory(TrxHistoryRequestDTO request);
}
