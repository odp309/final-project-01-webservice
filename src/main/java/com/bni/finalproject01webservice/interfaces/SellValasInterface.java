package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.sell_valas.request.DetailSellValasRequestDTO;
import com.bni.finalproject01webservice.dto.sell_valas.request.SellValasRequestDTO;
import com.bni.finalproject01webservice.dto.sell_valas.response.DetailSellValasResponseDTO;
import com.bni.finalproject01webservice.dto.sell_valas.response.SellValasResponseDTO;

public interface SellValasInterface {

    DetailSellValasResponseDTO detailSellValas(DetailSellValasRequestDTO request);

    SellValasResponseDTO sellValas(SellValasRequestDTO request);
}
