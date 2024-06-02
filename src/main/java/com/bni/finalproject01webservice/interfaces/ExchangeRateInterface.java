package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.request.ExchangeRateRequestDTO;
import com.bni.finalproject01webservice.dto.response.ExchangeRateResponseDTO;
import com.bni.finalproject01webservice.dto.response.FrankfurterResponseDTO;

import java.util.List;

public interface ExchangeRateInterface {

    FrankfurterResponseDTO addExchangeRateFrankfurter();

    ExchangeRateResponseDTO getExchangeRate(ExchangeRateRequestDTO request);

    List<ExchangeRateResponseDTO> getAllExchangeRate();
}
