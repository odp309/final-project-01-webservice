package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.currency.response.CurrencyResponseDTO;
import com.bni.finalproject01webservice.dto.init.response.InitResponseDTO;

import java.util.List;

public interface CurrencyInterface {

    InitResponseDTO initCurrency();

    List<CurrencyResponseDTO> getAllCurrency();
}
