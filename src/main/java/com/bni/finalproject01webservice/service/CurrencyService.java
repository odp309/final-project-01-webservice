package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.InitResponseDTO;
import com.bni.finalproject01webservice.interfaces.CurrencyInterface;
import com.bni.finalproject01webservice.model.Currency;
import com.bni.finalproject01webservice.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class CurrencyService implements CurrencyInterface {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InitResponseDTO initCurrency() {
        Currency currUSD = currencyRepository.findByCode("USD");
        Currency currSGD = currencyRepository.findByCode("SGD");
        Currency currJPY = currencyRepository.findByCode("JPY");
        Currency currEUR = currencyRepository.findByCode("EUR");
        Currency currGBP = currencyRepository.findByCode("GBP");
        Currency currAUD = currencyRepository.findByCode("AUD");
        Currency currMYR = currencyRepository.findByCode("MYR");
        Currency currNZD = currencyRepository.findByCode("NZD");
        Currency currTHB = currencyRepository.findByCode("THB");
        Currency currCNY = currencyRepository.findByCode("CNY");

        Currency USD = new Currency();
        Currency SGD = new Currency();
        Currency JPY = new Currency();
        Currency EUR = new Currency();
        Currency GBP = new Currency();
        Currency AUD = new Currency();
        Currency MYR = new Currency();
        Currency NZD = new Currency();
        Currency THB = new Currency();
        Currency CNY = new Currency();

        if (currUSD == null) {
            USD.setCode("USD");
            USD.setCreatedAt(new Date());
            currencyRepository.save(USD);
        }

        if (currSGD == null) {
            SGD.setCode("SGD");
            SGD.setCreatedAt(new Date());
            currencyRepository.save(SGD);
        }

        if (currJPY == null) {
            JPY.setCode("JPY");
            JPY.setCreatedAt(new Date());
            currencyRepository.save(JPY);
        }

        if (currEUR == null) {
            EUR.setCode("EUR");
            EUR.setCreatedAt(new Date());
            currencyRepository.save(EUR);
        }

        if (currGBP == null) {
            GBP.setCode("GBP");
            GBP.setCreatedAt(new Date());
            currencyRepository.save(GBP);
        }

        if (currAUD == null) {
            AUD.setCode("AUD");
            AUD.setCreatedAt(new Date());
            currencyRepository.save(AUD);
        }

        if (currMYR == null) {
            MYR.setCode("MYR");
            MYR.setCreatedAt(new Date());
            currencyRepository.save(MYR);
        }

        if (currNZD == null) {
            NZD.setCode("NZD");
            NZD.setCreatedAt(new Date());
            currencyRepository.save(NZD);
        }

        if (currTHB == null) {
            THB.setCode("THB");
            THB.setCreatedAt(new Date());
            currencyRepository.save(THB);
        }

        if (currCNY == null) {
            CNY.setCode("CNY");
            CNY.setCreatedAt(new Date());
            currencyRepository.save(CNY);
        }

        InitResponseDTO response = new InitResponseDTO();
        response.setMessage("Currency init done!");

        return response;
    }
}
