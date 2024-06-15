package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.buy_valas.request.BuyValasRequestDTO;
import com.bni.finalproject01webservice.dto.buy_valas.request.DetailBuyValasRequestDTO;
import com.bni.finalproject01webservice.dto.buy_valas.response.BuyValasResponseDTO;
import com.bni.finalproject01webservice.dto.buy_valas.response.DetailBuyValasResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface BuyValasInterface {

    DetailBuyValasResponseDTO detailBuyValas(DetailBuyValasRequestDTO request);

    BuyValasResponseDTO buyValas(BuyValasRequestDTO request);

    //////////////////////////////// VERSION 2.0 BLOCK ////////////////////////////////

    DetailBuyValasResponseDTO detailBuyValas(DetailBuyValasRequestDTO request, HttpServletRequest headerRequest);

    BuyValasResponseDTO buyValas(BuyValasRequestDTO request, HttpServletRequest headerRequest);
}
