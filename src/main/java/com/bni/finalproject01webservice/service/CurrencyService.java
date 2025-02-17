package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.currency.request.GetMinimumCurrencyRequestDTO;
import com.bni.finalproject01webservice.dto.currency.response.CurrencyResponseDTO;
import com.bni.finalproject01webservice.dto.currency.response.GetMinimumCurrencyResponseDTO;
import com.bni.finalproject01webservice.dto.init.response.InitResponseDTO;
import com.bni.finalproject01webservice.interfaces.CurrencyInterface;
import com.bni.finalproject01webservice.interfaces.DateTimeInterface;
import com.bni.finalproject01webservice.model.Currency;
import com.bni.finalproject01webservice.repository.CurrencyRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CurrencyService implements CurrencyInterface {

    private final CurrencyRepository currencyRepository;

    private final DateTimeInterface dateTimeService;

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
            USD.setMinimumSell(BigDecimal.ONE);
            USD.setMinimumTransfer(BigDecimal.ONE);
            USD.setMinimumWithdrawal(BigDecimal.valueOf(100));
            USD.setFlagIcon("https://i.imgur.com/KR2l4rP.png");
            USD.setLandmarkIcon("https://i.imgur.com/rNo8qiE.png");
            USD.setCreatedAt(dateTimeService.getCurrentDateTimeInJakarta());
            currencyRepository.save(USD);
        }

        if (currSGD == null) {
            SGD.setCode("SGD");
            SGD.setName("Singapore dollar");
            SGD.setMinimumDeposit(BigDecimal.valueOf(10));
            SGD.setMinimumBuy(BigDecimal.ONE);
            SGD.setMinimumSell(BigDecimal.ONE);
            SGD.setMinimumTransfer(BigDecimal.ONE);
            SGD.setMinimumWithdrawal(BigDecimal.valueOf(100));
            SGD.setFlagIcon("https://i.imgur.com/c8QU4XJ.png");
            SGD.setLandmarkIcon("https://i.imgur.com/rNo8qiE.png");
            SGD.setCreatedAt(dateTimeService.getCurrentDateTimeInJakarta());
            currencyRepository.save(SGD);
        }

        if (currJPY == null) {
            JPY.setCode("JPY");
            JPY.setName("Japanese yen");
            JPY.setMinimumDeposit(BigDecimal.valueOf(1000));
            JPY.setMinimumBuy(BigDecimal.ONE);
            JPY.setMinimumSell(BigDecimal.ONE);
            JPY.setMinimumTransfer(BigDecimal.ONE);
            JPY.setMinimumWithdrawal(BigDecimal.valueOf(10000));
            JPY.setFlagIcon("https://i.imgur.com/cEeytUt.png");
            JPY.setLandmarkIcon("https://i.imgur.com/rNo8qiE.png");
            JPY.setCreatedAt(dateTimeService.getCurrentDateTimeInJakarta());
            currencyRepository.save(JPY);
        }

        if (currEUR == null) {
            EUR.setCode("EUR");
            EUR.setName("Euro");
            EUR.setMinimumDeposit(BigDecimal.valueOf(10));
            EUR.setMinimumBuy(BigDecimal.ONE);
            EUR.setMinimumSell(BigDecimal.ONE);
            EUR.setMinimumTransfer(BigDecimal.ONE);
            EUR.setMinimumWithdrawal(BigDecimal.valueOf(100));
            EUR.setFlagIcon("https://i.imgur.com/lBFrzJl.png");
            EUR.setLandmarkIcon("https://i.imgur.com/rNo8qiE.png");
            EUR.setCreatedAt(dateTimeService.getCurrentDateTimeInJakarta());
            currencyRepository.save(EUR);
        }

        if (currGBP == null) {
            GBP.setCode("GBP");
            GBP.setName("Pound sterling");
            GBP.setMinimumDeposit(BigDecimal.valueOf(10));
            GBP.setMinimumBuy(BigDecimal.ONE);
            GBP.setMinimumSell(BigDecimal.ONE);
            GBP.setMinimumTransfer(BigDecimal.ONE);
            GBP.setMinimumWithdrawal(BigDecimal.valueOf(50));
            GBP.setFlagIcon("https://i.imgur.com/9YF70SA.png");
            GBP.setLandmarkIcon("https://i.imgur.com/rNo8qiE.png");
            GBP.setCreatedAt(dateTimeService.getCurrentDateTimeInJakarta());
            currencyRepository.save(GBP);
        }

        if (currAUD == null) {
            AUD.setCode("AUD");
            AUD.setName("Australian dollar");
            AUD.setMinimumDeposit(BigDecimal.valueOf(10));
            AUD.setMinimumBuy(BigDecimal.ONE);
            AUD.setMinimumSell(BigDecimal.ONE);
            AUD.setMinimumTransfer(BigDecimal.ONE);
            AUD.setMinimumWithdrawal(BigDecimal.valueOf(100));
            AUD.setFlagIcon("https://i.imgur.com/ouYv3jA.png");
            AUD.setLandmarkIcon("https://i.imgur.com/rNo8qiE.png");
            AUD.setCreatedAt(dateTimeService.getCurrentDateTimeInJakarta());
            currencyRepository.save(AUD);
        }

        if (currMYR == null) {
            MYR.setCode("MYR");
            MYR.setName("Malaysian ringgit");
            MYR.setMinimumDeposit(BigDecimal.valueOf(10));
            MYR.setMinimumBuy(BigDecimal.ONE);
            MYR.setMinimumSell(BigDecimal.ONE);
            MYR.setMinimumTransfer(BigDecimal.ONE);
            MYR.setMinimumWithdrawal(BigDecimal.valueOf(100));
            MYR.setFlagIcon("https://i.imgur.com/Ccfnua7.png");
            MYR.setLandmarkIcon("https://i.imgur.com/rNo8qiE.png");
            MYR.setCreatedAt(dateTimeService.getCurrentDateTimeInJakarta());
            currencyRepository.save(MYR);
        }

        if (currNZD == null) {
            NZD.setCode("NZD");
            NZD.setName("New Zealand dollar");
            NZD.setMinimumDeposit(BigDecimal.valueOf(100));
            NZD.setMinimumBuy(BigDecimal.ONE);
            NZD.setMinimumSell(BigDecimal.ONE);
            NZD.setMinimumTransfer(BigDecimal.ONE);
            NZD.setMinimumWithdrawal(BigDecimal.valueOf(100));
            NZD.setFlagIcon("https://i.imgur.com/Rcymbsa.png");
            NZD.setLandmarkIcon("https://i.imgur.com/rNo8qiE.png");
            NZD.setCreatedAt(dateTimeService.getCurrentDateTimeInJakarta());
            currencyRepository.save(NZD);
        }

        if (currTHB == null) {
            THB.setCode("THB");
            THB.setName("Thai baht");
            THB.setMinimumDeposit(BigDecimal.valueOf(100));
            THB.setMinimumBuy(BigDecimal.ONE);
            THB.setMinimumSell(BigDecimal.ONE);
            THB.setMinimumTransfer(BigDecimal.ONE);
            THB.setMinimumWithdrawal(BigDecimal.valueOf(1000));
            THB.setFlagIcon("https://i.imgur.com/dvcrF0R.png");
            THB.setLandmarkIcon("https://i.imgur.com/rNo8qiE.png");
            THB.setCreatedAt(dateTimeService.getCurrentDateTimeInJakarta());
            currencyRepository.save(THB);
        }

        if (currCNY == null) {
            CNY.setCode("CNY");
            CNY.setName("Chinese yuan");
            CNY.setMinimumDeposit(BigDecimal.valueOf(100));
            CNY.setMinimumBuy(BigDecimal.ONE);
            CNY.setMinimumSell(BigDecimal.ONE);
            CNY.setMinimumTransfer(BigDecimal.ONE);
            CNY.setMinimumWithdrawal(BigDecimal.valueOf(100));
            CNY.setFlagIcon("https://i.imgur.com/NuyGX6o.png");
            CNY.setLandmarkIcon("https://i.imgur.com/rNo8qiE.png");
            CNY.setCreatedAt(dateTimeService.getCurrentDateTimeInJakarta());
            currencyRepository.save(CNY);
        }

        if (currCAD == null) {
            CAD.setCode("CAD");
            CAD.setName("Canadian dollar");
            CAD.setMinimumDeposit(BigDecimal.valueOf(10));
            CAD.setMinimumBuy(BigDecimal.ONE);
            CAD.setMinimumSell(BigDecimal.ONE);
            CAD.setMinimumTransfer(BigDecimal.ONE);
            CAD.setMinimumWithdrawal(BigDecimal.valueOf(100));
            CAD.setFlagIcon("https://i.imgur.com/vMs98rY.png");
            CAD.setLandmarkIcon("https://i.imgur.com/rNo8qiE.png");
            CAD.setCreatedAt(dateTimeService.getCurrentDateTimeInJakarta());
            currencyRepository.save(CAD);
        }

        if (currCHF == null) {
            CHF.setCode("CHF");
            CHF.setName("Swiss Franc");
            CHF.setMinimumDeposit(BigDecimal.valueOf(10));
            CHF.setMinimumBuy(BigDecimal.ONE);
            CHF.setMinimumSell(BigDecimal.ONE);
            CHF.setMinimumTransfer(BigDecimal.ONE);
            CHF.setMinimumWithdrawal(BigDecimal.valueOf(100));
            CHF.setFlagIcon("https://i.imgur.com/b1B5OyE.png");
            CHF.setLandmarkIcon("https://i.imgur.com/rNo8qiE.png");
            CHF.setCreatedAt(dateTimeService.getCurrentDateTimeInJakarta());
            currencyRepository.save(CHF);
        }

        if (currHKD == null) {
            HKD.setCode("HKD");
            HKD.setName("Hong Kong dollar");
            HKD.setMinimumDeposit(BigDecimal.valueOf(100));
            HKD.setMinimumBuy(BigDecimal.ONE);
            HKD.setMinimumSell(BigDecimal.ONE);
            HKD.setMinimumTransfer(BigDecimal.ONE);
            HKD.setMinimumWithdrawal(BigDecimal.valueOf(500));
            HKD.setFlagIcon("https://i.imgur.com/NhwC9kk.png");
            HKD.setLandmarkIcon("https://i.imgur.com/rNo8qiE.png");
            HKD.setCreatedAt(dateTimeService.getCurrentDateTimeInJakarta());
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
                    currencyResponseDTO.setCreatedAt(String.valueOf(currency.getCreatedAt()));
                    currencyResponseDTO.setUpdatedAt(String.valueOf(currency.getUpdatedAt()));
                    return currencyResponseDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public GetMinimumCurrencyResponseDTO getMinimumBuy(GetMinimumCurrencyRequestDTO request) {
        Currency currency = currencyRepository.findByCode(request.getCurrencyCode());

        GetMinimumCurrencyResponseDTO response = new GetMinimumCurrencyResponseDTO();
        response.setMinimum(currency.getMinimumBuy());

        return response;
    }

    @Override
    public GetMinimumCurrencyResponseDTO getMinimumSell(GetMinimumCurrencyRequestDTO request) {
        Currency currency = currencyRepository.findByCode(request.getCurrencyCode());

        GetMinimumCurrencyResponseDTO response = new GetMinimumCurrencyResponseDTO();
        response.setMinimum(currency.getMinimumSell());

        return response;
    }

    @Override
    public GetMinimumCurrencyResponseDTO getMinimumTransfer(GetMinimumCurrencyRequestDTO request) {
        Currency currency = currencyRepository.findByCode(request.getCurrencyCode());

        GetMinimumCurrencyResponseDTO response = new GetMinimumCurrencyResponseDTO();
        response.setMinimum(currency.getMinimumTransfer());

        return response;
    }

    @Override
    public GetMinimumCurrencyResponseDTO getMinimumDeposit(GetMinimumCurrencyRequestDTO request) {
        Currency currency = currencyRepository.findByCode(request.getCurrencyCode());

        GetMinimumCurrencyResponseDTO response = new GetMinimumCurrencyResponseDTO();
        response.setMinimum(currency.getMinimumDeposit());

        return response;
    }

    @Override
    public GetMinimumCurrencyResponseDTO getMinimumWithdrawal(GetMinimumCurrencyRequestDTO request) {
        Currency currency = currencyRepository.findByCode(request.getCurrencyCode());

        GetMinimumCurrencyResponseDTO response = new GetMinimumCurrencyResponseDTO();
        response.setMinimum(currency.getMinimumWithdrawal());

        return response;
    }
}