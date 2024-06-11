package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.currency.request.GetMinimumCurrencyRequestDTO;
import com.bni.finalproject01webservice.dto.currency.response.CurrencyResponseDTO;
import com.bni.finalproject01webservice.dto.currency.response.GetMinimumCurrencyResponseDTO;
import com.bni.finalproject01webservice.dto.init.response.InitResponseDTO;

import java.util.List;

public interface CurrencyInterface {

    InitResponseDTO initCurrency();

    List<CurrencyResponseDTO> getAllCurrency();

    GetMinimumCurrencyResponseDTO getMinimumBuy(GetMinimumCurrencyRequestDTO request);

    GetMinimumCurrencyResponseDTO getMinimumSell(GetMinimumCurrencyRequestDTO request);

    GetMinimumCurrencyResponseDTO getMinimumTransfer(GetMinimumCurrencyRequestDTO request);

    GetMinimumCurrencyResponseDTO getMinimumDeposit(GetMinimumCurrencyRequestDTO request);

    GetMinimumCurrencyResponseDTO getMinimumWithdrawal(GetMinimumCurrencyRequestDTO request);
}
