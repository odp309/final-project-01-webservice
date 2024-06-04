package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.configuration.exceptions.UserException;
import com.bni.finalproject01webservice.dto.request.BuyValasRequestDTO;
import com.bni.finalproject01webservice.dto.request.DetailBuyValasRequestDTO;
import com.bni.finalproject01webservice.dto.response.BuyValasResponseDTO;
import com.bni.finalproject01webservice.dto.response.DetailBuyValasResponseDTO;
import com.bni.finalproject01webservice.interfaces.BuyValasInterface;
import com.bni.finalproject01webservice.model.BankAccount;
import com.bni.finalproject01webservice.model.ExchangeRate;
import com.bni.finalproject01webservice.model.User;
import com.bni.finalproject01webservice.model.Wallet;
import com.bni.finalproject01webservice.repository.BankAccountRepository;
import com.bni.finalproject01webservice.repository.ExchangeRateRepository;
import com.bni.finalproject01webservice.repository.UserRepository;
import com.bni.finalproject01webservice.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuyValasService implements BuyValasInterface {


    private final ExchangeRateRepository exchangeRateRepository;
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WalletRepository walletRepository;

    public DetailBuyValasResponseDTO detailBuyValas(DetailBuyValasRequestDTO request) {


        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRate(request.currencyCode);

        DetailBuyValasResponseDTO response = new DetailBuyValasResponseDTO();

        response.setAmountToPay(request.getAmountToBuy().multiply(exchangeRate.getBuyRate()));
        return  response;
    }


    public BuyValasResponseDTO buyValas(BuyValasRequestDTO request) {

        BuyValasResponseDTO response = new BuyValasResponseDTO();

        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRate(request.currencyCode);
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(request.getAccountNumber());

        UUID idWallet = request.getWalletId();
        Wallet wallet = walletRepository.findById(idWallet)
                .orElseThrow(() -> new RuntimeException("Wallet not found for ID: " + request.getWalletId()));

        UUID id = bankAccount.getUser().getId();
        User users = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found for ID: " + id));

        String encodedPin = users.getPin();
        String rawPin = request.getPin();

        boolean checkPin = passwordEncoder.matches(rawPin, encodedPin);

        if (!checkPin) {
            throw new UserException("Invalid pin!");
        }
        else
        {
//            Wallet newWallet = new Wallet();
            wallet.setBalance(wallet.getBalance().add(request.getAmountToBuy()));
            wallet.setUpdatedAt(new Date());
            walletRepository.save(wallet);


            BigDecimal currBalance = bankAccount.getBalance();
            BigDecimal paidPrice = request.getAmountToBuy().multiply(exchangeRate.getBuyRate());
            bankAccount.setBalance(currBalance.subtract(paidPrice));
            bankAccount.setUpdatedAt(new Date());
            bankAccountRepository.save(bankAccount);

            response.setAmountToBuy(request.getAmountToBuy());
            response.setAmountToPay(request.getAmountToBuy().multiply(exchangeRate.getBuyRate()));
            response.setCurrencyCode(request.getCurrencyCode());
            response.setAccountNumber(request.getAccountNumber());
        }


//        response.setEncodedPin(encodedPin);
//        response.setRawPin(rawPin);
//        response.setCheckPin(checkPin);
//        response.setPin(pin)


        return response;
    }

}
