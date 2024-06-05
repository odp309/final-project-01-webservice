package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.request.DetailSellValasRequestDTO;
import com.bni.finalproject01webservice.dto.response.DetailBuyValasResponseDTO;
import com.bni.finalproject01webservice.dto.response.DetailSellValasResponseDTO;

public interface SellValasInterface {

    DetailSellValasResponseDTO detailSellValas(DetailSellValasRequestDTO request);

}
