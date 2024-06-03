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


//        ExchangeRate exchangeRate = BuyValasRepository.findExchangeRate(request.currencyCode);
// ini pake repository buyvalasREpo atau mau pake exchangeRate Repo yag single yg udah ada
        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRate(request.currencyCode);

        DetailBuyValasResponseDTO response = new DetailBuyValasResponseDTO();

        response.setAmountToPay(request.getAmountToBuy().multiply(exchangeRate.getBuyRate()));
        return  response;
    }

    public BuyValasResponseDTO buyValas(BuyValasRequestDTO request) {

        BuyValasResponseDTO response = new BuyValasResponseDTO();

        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRate(request.currencyCode);
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(request.getAccountNumber());
        Optional<Wallet> wallet = walletRepository.findById(request.getWalletId());

        UUID id = bankAccount.getUser().getId();
        Optional<User> users = userRepository.findById(id);

        String encodedPin = users.get().getPin();
        String rawPin = request.getPin();

        boolean checkPin = passwordEncoder.matches(rawPin, encodedPin);

        if (!checkPin) {
            throw new UserException("Invalid pin!");
        }
        else
        {
            Wallet newWallet = new Wallet();
            newWallet.setBalance(newWallet.getBalance().add(request.getAmountToBuy()));
            newWallet.setUpdatedAt(new Date());
            walletRepository.save(newWallet);

            BankAccount balance = new BankAccount();
            balance.setBalance(wallet.get().getBalance().subtract(request.getAmountToBuy().multiply(exchangeRate.getBuyRate())));
            balance.setUpdatedAt(new Date());
            bankAccountRepository.save(balance);

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
