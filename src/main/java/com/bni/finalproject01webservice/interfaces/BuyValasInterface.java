package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.buy_valas.request.BuyValasRequestDTO;
import com.bni.finalproject01webservice.dto.buy_valas.request.DetailBuyValasRequestDTO;
import com.bni.finalproject01webservice.dto.buy_valas.response.BuyValasResponseDTO;
import com.bni.finalproject01webservice.dto.buy_valas.response.DetailBuyValasResponseDTO;

public interface BuyValasInterface {

    DetailBuyValasResponseDTO detailBuyValas(DetailBuyValasRequestDTO request);

    BuyValasResponseDTO buyValas(BuyValasRequestDTO request);
}
