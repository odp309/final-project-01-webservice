package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.currency.response.CurrencyResponseDTO;
import com.bni.finalproject01webservice.dto.init.response.InitResponseDTO;
import com.bni.finalproject01webservice.interfaces.CurrencyInterface;
import com.bni.finalproject01webservice.model.Currency;
import com.bni.finalproject01webservice.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
            USD.setMinimumDeposit(BigDecimal.valueOf(10));
            USD.setMinimumBuy(BigDecimal.ONE);
            USD.setIcon("../../../assets/icons/flags/United_States.png");
            USD.setCreatedAt(new Date());
            currencyRepository.save(USD);
        }

        if (currSGD == null) {
            SGD.setCode("SGD");
            SGD.setName("Singapore dollar");
            SGD.setMinimumDeposit(BigDecimal.valueOf(10));
            SGD.setMinimumBuy(BigDecimal.ONE);
            SGD.setIcon("../../../assets/icons/flags/Singapore.png");
            SGD.setCreatedAt(new Date());
            currencyRepository.save(SGD);
        }

        if (currJPY == null) {
            JPY.setCode("JPY");
            JPY.setName("Japanese yen");
            JPY.setMinimumDeposit(BigDecimal.valueOf(1000));
            JPY.setMinimumBuy(BigDecimal.ONE);
            JPY.setIcon("../../../assets/icons/flags/Japan.png");
            JPY.setCreatedAt(new Date());
            currencyRepository.save(JPY);
        }

        if (currEUR == null) {
            EUR.setCode("EUR");
            EUR.setName("Euro");
            EUR.setMinimumDeposit(BigDecimal.valueOf(10));
            EUR.setMinimumBuy(BigDecimal.ONE);
            EUR.setIcon("../../../assets/icons/flags/European_Union.png");
            EUR.setCreatedAt(new Date());
            currencyRepository.save(EUR);
        }

        if (currGBP == null) {
            GBP.setCode("GBP");
            GBP.setName("Pound sterling");
            GBP.setMinimumDeposit(BigDecimal.valueOf(10));
            GBP.setMinimumBuy(BigDecimal.ONE);
            GBP.setIcon("../../../assets/icons/flags/UK.png");
            GBP.setCreatedAt(new Date());
            currencyRepository.save(GBP);
        }

        if (currAUD == null) {
            AUD.setCode("AUD");
            AUD.setName("Australian dollar");
            AUD.setMinimumDeposit(BigDecimal.valueOf(10));
            AUD.setMinimumBuy(BigDecimal.ONE);
            AUD.setIcon("../../../assets/icons/flags/Australia.png");
            AUD.setCreatedAt(new Date());
            currencyRepository.save(AUD);
        }

        if (currMYR == null) {
            MYR.setCode("MYR");
            MYR.setName("Malaysian ringgit");
            MYR.setMinimumDeposit(BigDecimal.valueOf(10));
            MYR.setMinimumBuy(BigDecimal.ONE);
            MYR.setIcon("../../../assets/icons/flags/Malaysia.png");
            MYR.setCreatedAt(new Date());
            currencyRepository.save(MYR);
        }

        if (currNZD == null) {
            NZD.setCode("NZD");
            NZD.setName("New Zealand dollar");
            NZD.setMinimumDeposit(BigDecimal.valueOf(100));
            NZD.setMinimumBuy(BigDecimal.ONE);
            NZD.setIcon("../../../assets/icons/flags/New_Zealand.png");
            NZD.setCreatedAt(new Date());
            currencyRepository.save(NZD);
        }

        if (currTHB == null) {
            THB.setCode("THB");
            THB.setName("Thai baht");
            THB.setMinimumDeposit(BigDecimal.valueOf(100));
            THB.setMinimumBuy(BigDecimal.ONE);
            THB.setIcon("../../../assets/icons/flags/Thailand.png");
            THB.setCreatedAt(new Date());
            currencyRepository.save(THB);
        }

        if (currCNY == null) {
            CNY.setCode("CNY");
            CNY.setName("Chinese yuan");
            CNY.setMinimumDeposit(BigDecimal.valueOf(100));
            CNY.setMinimumBuy(BigDecimal.ONE);
            CNY.setIcon("../../../assets/icons/flags/China.png");
            CNY.setCreatedAt(new Date());
            currencyRepository.save(CNY);
        }

        if (currCAD == null) {
            CAD.setCode("CAD");
            CAD.setName("Canadian dollar");
            CAD.setMinimumDeposit(BigDecimal.valueOf(10));
            CAD.setMinimumBuy(BigDecimal.ONE);
            CAD.setIcon("../../../assets/icons/flags/Canada.png");
            CAD.setCreatedAt(new Date());
            currencyRepository.save(CAD);
        }

        if (currCHF == null) {
            CHF.setCode("CHF");
            CHF.setName("Swiss Franc");
            CHF.setMinimumDeposit(BigDecimal.valueOf(10));
            CHF.setMinimumBuy(BigDecimal.ONE);
            CHF.setIcon("../../../assets/icons/flags/Swiss.png");
            CHF.setCreatedAt(new Date());
            currencyRepository.save(CHF);
        }

        if (currHKD == null) {
            HKD.setCode("HKD");
            HKD.setName("Hong Kong dollar");
            HKD.setMinimumDeposit(BigDecimal.valueOf(100));
            HKD.setMinimumBuy(BigDecimal.ONE);
            HKD.setIcon("../../../assets/icons/flags/Hong_Kong.png");
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
