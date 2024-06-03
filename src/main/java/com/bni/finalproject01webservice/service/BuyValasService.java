package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.request.DetailBuyValasRequestDTO;
import com.bni.finalproject01webservice.dto.response.DetailBuyValasResponseDTO;
import com.bni.finalproject01webservice.interfaces.BuyValasInterface;
import com.bni.finalproject01webservice.model.ExchangeRate;
import com.bni.finalproject01webservice.repository.BuyValasRepository;
import com.bni.finalproject01webservice.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuyValasService implements BuyValasInterface {

    private final BuyValasRepository BuyValasRepository;
    private final ExchangeRateRepository ExchangeRateRepository;
    public DetailBuyValasResponseDTO detailBuyValas(DetailBuyValasRequestDTO request) {


//        ExchangeRate exchangeRate = BuyValasRepository.findExchangeRate(request.currencyCode);
// ini pake repository buyvalasREpo atau mau pake exchangeRate Repo yag single yg udah ada
        ExchangeRate exchangeRate = ExchangeRateRepository.findExchangeRate(request.currencyCode);

        DetailBuyValasResponseDTO response = new DetailBuyValasResponseDTO();

        response.setAmountToPay(request.getAmountToBuy().multiply(exchangeRate.getBuyRate()));
        return  response;
    }

}
