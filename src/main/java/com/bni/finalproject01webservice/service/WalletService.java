package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.configuration.exceptions.TransactionException;
import com.bni.finalproject01webservice.configuration.exceptions.UserException;
import com.bni.finalproject01webservice.configuration.exceptions.WalletException;
import com.bni.finalproject01webservice.dto.buy_valas.request.BuyValasRequestDTO;
import com.bni.finalproject01webservice.dto.buy_valas.response.BuyValasResponseDTO;
import com.bni.finalproject01webservice.dto.wallet.request.AddWalletRequestDTO;
import com.bni.finalproject01webservice.dto.wallet.response.GetAllWalletResponseDTO;
import com.bni.finalproject01webservice.dto.wallet.response.WalletResponseDTO;
import com.bni.finalproject01webservice.interfaces.BuyValasInterface;
import com.bni.finalproject01webservice.interfaces.ResourceRequestCheckerInterface;
import com.bni.finalproject01webservice.interfaces.WalletInterface;
import com.bni.finalproject01webservice.model.BankAccount;
import com.bni.finalproject01webservice.model.Currency;
import com.bni.finalproject01webservice.model.User;
import com.bni.finalproject01webservice.model.Wallet;
import com.bni.finalproject01webservice.repository.BankAccountRepository;
import com.bni.finalproject01webservice.repository.CurrencyRepository;
import com.bni.finalproject01webservice.repository.UserRepository;
import com.bni.finalproject01webservice.repository.WalletRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletService implements WalletInterface {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    private final BankAccountRepository bankAccountRepository;

    private final PasswordEncoder passwordEncoder;
    private final BuyValasInterface buyValasService;
    private final ResourceRequestCheckerInterface resourceRequestCheckerService;

    @Override
    public GetAllWalletResponseDTO getWallet(String accountNumber) {
        List<Wallet> wallets = walletRepository.findByBankAccountAccountNumber(accountNumber, Sort.by(Sort.Direction.ASC, "currency.name"));
        if (wallets == null || wallets.isEmpty()) {
            throw new RuntimeException("wallet not found");
        }

        Wallet firstWallet = wallets.get(0);
        GetAllWalletResponseDTO response = new GetAllWalletResponseDTO();
        response.setAccountNumber(firstWallet.getBankAccount().getAccountNumber());
        //response.setId_user(firstWallet.getUser().get);

        List<GetAllWalletResponseDTO.WalletInfo> walletInfos = wallets.stream()
                .map(wallet -> {
                    GetAllWalletResponseDTO.WalletInfo walletInfo = new GetAllWalletResponseDTO.WalletInfo();
                    walletInfo.setCurrencyId(wallet.getCurrency().getCode());
                    walletInfo.setBalance(wallet.getBalance().floatValue());
                    return walletInfo;
                })
                .collect(Collectors.toList());

        response.setWallet(walletInfos);

        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WalletResponseDTO addWallet(AddWalletRequestDTO request) {
        Wallet currentWallet = walletRepository.findByBankAccountAccountNumberAndCurrencyCode(request.getAccountNumber(), request.getCurrencyCode());

        if (currentWallet != null) {
            throw new WalletException("Wallet already exist!");
        }

        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new UserException("User not found!"));
        Currency currency = currencyRepository.findByCode(request.getCurrencyCode());
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(request.getAccountNumber());

        if (request.getAmountToBuy().compareTo(currency.getMinimumBuy()) < 0) {
            throw new TransactionException("Amount is less than the minimum deposit!");
        }

        boolean checkPin = passwordEncoder.matches(request.getPin(), user.getPin());

        if (!checkPin) {
            throw new UserException("Invalid pin!");
        }

        // create wallet with 0 balance
        Wallet newWallet = new Wallet();
        newWallet.setBalance(BigDecimal.valueOf(0));
        newWallet.setCreatedAt(new Date());
        newWallet.setBankAccount(bankAccount);
        newWallet.setUser(user);
        newWallet.setCurrency(currency);
        walletRepository.save(newWallet);

        // update the wallet with buy trx
        BuyValasRequestDTO buyValasRequest = new BuyValasRequestDTO();
        buyValasRequest.setWalletId(newWallet.getId());
        buyValasRequest.setAmountToBuy(request.getAmountToBuy());
        buyValasRequest.setPin(request.getPin());
        BuyValasResponseDTO buyValasResponse = buyValasService.buyValas(buyValasRequest);

        WalletResponseDTO response = new WalletResponseDTO();
        response.setUserId(user.getId());
        response.setCurrentBalance(newWallet.getBalance());
        response.setCreatedAt(String.valueOf(newWallet.getCreatedAt()));
        response.setBuyValasResponse(buyValasResponse);

        return response;
    }

    //////////////////////////////// VERSION 2.0 BLOCK ////////////////////////////////

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WalletResponseDTO addWallet(AddWalletRequestDTO request, HttpServletRequest headerRequest) {

        // bank account request checking
        List<String> bankAccountNumbers = resourceRequestCheckerService.bankAccountChecker(headerRequest);
        if (!bankAccountNumbers.contains(request.getAccountNumber())) {
            throw new UserException("Un-match request!");
        }

        UUID userId = resourceRequestCheckerService.extractIdFromToken(headerRequest);
        Wallet currentWallet = walletRepository.findByBankAccountAccountNumberAndCurrencyCode(request.getAccountNumber(), request.getCurrencyCode());

        if (currentWallet != null) {
            throw new WalletException("Wallet already exist!");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found!"));
        Currency currency = currencyRepository.findByCode(request.getCurrencyCode());
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(request.getAccountNumber());

        if (request.getAmountToBuy().compareTo(currency.getMinimumBuy()) < 0) {
            throw new TransactionException("Amount is less than the minimum deposit!");
        }

        boolean checkPin = passwordEncoder.matches(request.getPin(), user.getPin());

        if (!checkPin) {
            throw new UserException("Invalid pin!");
        }

        // create wallet with 0 balance
        Wallet newWallet = new Wallet();
        newWallet.setBalance(BigDecimal.valueOf(0));
        newWallet.setCreatedAt(new Date());
        newWallet.setBankAccount(bankAccount);
        newWallet.setUser(user);
        newWallet.setCurrency(currency);
        walletRepository.save(newWallet);

        // update the wallet with buy trx
        BuyValasRequestDTO buyValasRequest = new BuyValasRequestDTO();
        buyValasRequest.setWalletId(newWallet.getId());
        buyValasRequest.setAmountToBuy(request.getAmountToBuy());
        buyValasRequest.setPin(request.getPin());
        BuyValasResponseDTO buyValasResponse = buyValasService.buyValas(buyValasRequest);

        WalletResponseDTO response = new WalletResponseDTO();
        response.setUserId(user.getId());
        response.setCurrentBalance(newWallet.getBalance());
        response.setCreatedAt(String.valueOf(newWallet.getCreatedAt()));
        response.setBuyValasResponse(buyValasResponse);

        return response;
    }
}