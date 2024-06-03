package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.request.BuyValasRequestDTO;
import com.bni.finalproject01webservice.dto.request.DetailBuyValasRequestDTO;
import com.bni.finalproject01webservice.dto.response.BuyValasResponseDTO;
import com.bni.finalproject01webservice.dto.response.DetailBuyValasResponseDTO;

public interface BuyValasInterface {

    DetailBuyValasResponseDTO detailBuyValas(DetailBuyValasRequestDTO request);

    BuyValasResponseDTO buyValas(BuyValasRequestDTO request);
}
