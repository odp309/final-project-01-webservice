package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.request.DetailSellValasRequestDTO;
import com.bni.finalproject01webservice.dto.request.SellValasRequestDTO;
import com.bni.finalproject01webservice.dto.response.BuyValasResponseDTO;
import com.bni.finalproject01webservice.dto.response.DetailBuyValasResponseDTO;
import com.bni.finalproject01webservice.dto.response.DetailSellValasResponseDTO;
import com.bni.finalproject01webservice.dto.response.SellValasResponseDTO;

public interface SellValasInterface {

    DetailSellValasResponseDTO detailSellValas(DetailSellValasRequestDTO request);

    SellValasResponseDTO sellValas(SellValasRequestDTO request);
}
