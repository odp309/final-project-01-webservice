package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.financial_trx.request.FinancialTrxRequestDTO;
import com.bni.finalproject01webservice.dto.financial_trx.response.FinancialTrxResponseDTO;

public interface FinancialTrxInterface {

    FinancialTrxResponseDTO addFinancialTrx(FinancialTrxRequestDTO request);
}
