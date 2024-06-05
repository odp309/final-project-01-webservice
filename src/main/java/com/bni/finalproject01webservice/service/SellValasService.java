package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.request.DetailSellValasRequestDTO;
import com.bni.finalproject01webservice.dto.response.DetailBuyValasResponseDTO;
import com.bni.finalproject01webservice.dto.response.DetailSellValasResponseDTO;
import com.bni.finalproject01webservice.interfaces.SellValasInterface;
import com.bni.finalproject01webservice.model.ExchangeRate;
import com.bni.finalproject01webservice.repository.BankAccountRepository;
import com.bni.finalproject01webservice.repository.ExchangeRateRepository;
import com.bni.finalproject01webservice.repository.UserRepository;
import com.bni.finalproject01webservice.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SellValasService implements SellValasInterface {

    private final ExchangeRateRepository exchangeRateRepository;
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WalletRepository walletRepository;

    public DetailSellValasResponseDTO detailSellValas(DetailSellValasRequestDTO request) {

        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRate(request.currencyCode);

        DetailSellValasResponseDTO response = new DetailSellValasResponseDTO();
        response.setCurrencyCode(exchangeRate.getCurrency().getCode());
        response.setCurrencyName(exchangeRate.getCurrency().getName());
        response.setSellRate(exchangeRate.getSellRate());
        response.setTotalAmountToSell(request.getAmountToSell().multiply(exchangeRate.getBuyRate()));

        return response;
    }
}
