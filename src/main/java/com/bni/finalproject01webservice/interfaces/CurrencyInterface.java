package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.response.CurrencyResponseDTO;
import com.bni.finalproject01webservice.dto.response.InitResponseDTO;
import com.bni.finalproject01webservice.model.Currency;

import java.util.List;

public interface CurrencyInterface {

    InitResponseDTO initCurrency();

    List<CurrencyResponseDTO> getAllCurrency();
}
