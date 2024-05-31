package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.response.CurrencyResponseDTO;
import com.bni.finalproject01webservice.dto.response.InitResponseDTO;
import com.bni.finalproject01webservice.interfaces.CurrencyInterface;
import com.bni.finalproject01webservice.model.Currency;
import com.bni.finalproject01webservice.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        Currency currCAD = currencyRepository.findByCode("CAD");
        Currency currCHF = currencyRepository.findByCode("CHF");
        Currency currHKD = currencyRepository.findByCode("HKD");

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
        Currency CAD = new Currency();
        Currency CHF = new Currency();
        Currency HKD = new Currency();

        if (currUSD == null) {
            USD.setCode("USD");
            USD.setName("United States dollar");
            USD.setCreatedAt(new Date());
            currencyRepository.save(USD);
        }

        if (currSGD == null) {
            SGD.setCode("SGD");
            SGD.setName("Singapore dollar");
            SGD.setCreatedAt(new Date());
            currencyRepository.save(SGD);
        }

        if (currJPY == null) {
            JPY.setCode("JPY");
            JPY.setName("Japanese yen");
            JPY.setCreatedAt(new Date());
            currencyRepository.save(JPY);
        }

        if (currEUR == null) {
            EUR.setCode("EUR");
            EUR.setName("Euro");
            EUR.setCreatedAt(new Date());
            currencyRepository.save(EUR);
        }

        if (currGBP == null) {
            GBP.setCode("GBP");
            GBP.setName("Pound sterling");
            GBP.setCreatedAt(new Date());
            currencyRepository.save(GBP);
        }

        if (currAUD == null) {
            AUD.setCode("AUD");
            AUD.setName("Australian dollar");
            AUD.setCreatedAt(new Date());
            currencyRepository.save(AUD);
        }

        if (currMYR == null) {
            MYR.setCode("MYR");
            MYR.setName("Malaysian ringgit");
            MYR.setCreatedAt(new Date());
            currencyRepository.save(MYR);
        }

        if (currNZD == null) {
            NZD.setCode("NZD");
            NZD.setName("New Zealand dollar");
            NZD.setCreatedAt(new Date());
            currencyRepository.save(NZD);
        }

        if (currTHB == null) {
            THB.setCode("THB");
            THB.setName("Thai baht");
            THB.setCreatedAt(new Date());
            currencyRepository.save(THB);
        }

        if (currCNY == null) {
            CNY.setCode("CNY");
            CNY.setName("Chinese yuan");
            CNY.setCreatedAt(new Date());
            currencyRepository.save(CNY);
        }

        if (currCAD == null) {
            CAD.setCode("CAD");
            CAD.setName("Canadian dollar");
            CAD.setCreatedAt(new Date());
            currencyRepository.save(CAD);
        }

        if (currCHF == null) {
            CHF.setCode("CHF");
            CHF.setName("Swiss Franc");
            CHF.setCreatedAt(new Date());
            currencyRepository.save(CHF);
        }

        if (currHKD == null) {
            HKD.setCode("HKD");
            HKD.setName("Hong Kong dollar");
            HKD.setCreatedAt(new Date());
            currencyRepository.save(HKD);
        }

        InitResponseDTO response = new InitResponseDTO();
        response.setMessage("Currency init done!");

        return response;
    }

    @Override
    public List<CurrencyResponseDTO> getAllCurrency() {
        List<Currency> currencies = currencyRepository.findAll();

        return currencies.stream()
                .map(currency -> {
                    CurrencyResponseDTO currencyResponseDTO = new CurrencyResponseDTO();
                    currencyResponseDTO.setCode(currency.getCode());
                    currencyResponseDTO.setName(currency.getName());
                    currencyResponseDTO.setCreatedAt(currency.getCreatedAt());
                    currencyResponseDTO.setUpdatedAt(currency.getUpdatedAt());
                    return currencyResponseDTO;
                })
                .collect(Collectors.toList());
    }
}
