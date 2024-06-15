package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.sell_valas.request.DetailSellValasRequestDTO;
import com.bni.finalproject01webservice.dto.sell_valas.request.SellValasRequestDTO;
import com.bni.finalproject01webservice.dto.sell_valas.response.DetailSellValasResponseDTO;
import com.bni.finalproject01webservice.dto.sell_valas.response.SellValasResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface SellValasInterface {

    DetailSellValasResponseDTO detailSellValas(DetailSellValasRequestDTO request);

    SellValasResponseDTO sellValas(SellValasRequestDTO request);

    //////////////////////////////// VERSION 2.0 BLOCK ////////////////////////////////

    DetailSellValasResponseDTO detailSellValas(DetailSellValasRequestDTO request, HttpServletRequest headerRequest);

    SellValasResponseDTO sellValas(SellValasRequestDTO request, HttpServletRequest headerRequest);
}
