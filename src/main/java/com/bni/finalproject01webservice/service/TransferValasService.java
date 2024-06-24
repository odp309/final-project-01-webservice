package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.configuration.exceptions.TransactionException;
import com.bni.finalproject01webservice.configuration.exceptions.UserException;
import com.bni.finalproject01webservice.configuration.exceptions.WalletException;
import com.bni.finalproject01webservice.dto.financial_trx.request.FinancialTrxRequestDTO;
import com.bni.finalproject01webservice.dto.financial_trx.response.FinancialTrxResponseDTO;
import com.bni.finalproject01webservice.dto.transfer_valas.request.DetailTransferValasRequestDTO;
import com.bni.finalproject01webservice.dto.transfer_valas.request.TransferValasRequestDTO;
import com.bni.finalproject01webservice.dto.transfer_valas.response.DetailTransferValasResponseDTO;
import com.bni.finalproject01webservice.dto.transfer_valas.response.TransferValasResponseDTO;
import com.bni.finalproject01webservice.interfaces.DateTimeInterface;
import com.bni.finalproject01webservice.interfaces.FinancialTrxInterface;
import com.bni.finalproject01webservice.interfaces.ResourceRequestCheckerInterface;
import com.bni.finalproject01webservice.interfaces.TransferValasInterface;
import com.bni.finalproject01webservice.model.User;
import com.bni.finalproject01webservice.model.Wallet;
import com.bni.finalproject01webservice.repository.BankAccountRepository;
import com.bni.finalproject01webservice.repository.UserRepository;
import com.bni.finalproject01webservice.repository.WalletRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferValasService implements TransferValasInterface {

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    private final PasswordEncoder passwordEncoder;
    private final FinancialTrxInterface financialTrxService;
    private final ResourceRequestCheckerInterface resourceRequestCheckerService;
    private final DateTimeInterface dateTimeService;

    @Override
    public DetailTransferValasResponseDTO detailTransferValas(DetailTransferValasRequestDTO request) {

        Wallet senderWallet = walletRepository.findById(request.getSenderWalletId()).orElseThrow(() -> new WalletException("Wallet not found!"));

        if (senderWallet.getBankAccount().getAccountNumber().equals(request.getRecipientAccountNumber())) {
            throw new TransactionException("Transfers to the same account are not allowed!");
        }

        Wallet recipientWallet = walletRepository.findByBankAccountAccountNumberAndCurrencyCode(request.getRecipientAccountNumber(), senderWallet.getCurrency().getCode());

        if (recipientWallet == null) {
            throw new WalletException("Wallet not found!");
        }

        if (request.getAmountToTransfer().compareTo(senderWallet.getCurrency().getMinimumTransfer()) < 0) {
            throw new TransactionException("Amount is less than the minimum transfer!");
        }

        DetailTransferValasResponseDTO response = new DetailTransferValasResponseDTO();
        response.setFirstName(recipientWallet.getUser().getFirstName());
        response.setLastName(recipientWallet.getUser().getLastName());
        response.setCurrencyCode(recipientWallet.getCurrency().getCode());
        response.setAmountToTransfer(request.getAmountToTransfer());

        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransferValasResponseDTO transferValas(TransferValasRequestDTO request) {

        Wallet senderWallet = walletRepository.findById(request.getSenderWalletId()).orElseThrow(() -> new WalletException("Wallet not found!"));
        User senderUser = userRepository.findById(senderWallet.getUser().getId()).orElseThrow(() -> new UserException("User not found!"));

//        List<String> senderUserAccountNumbers = senderUser.getBankAccounts().stream()
//                .map(BankAccount::getAccountNumber)
//                .toList();
//
//        if (senderUserAccountNumbers.contains(request.getRecipientAccountNumber())) {
//            throw new TransactionException("Transfers to the same account are not allowed!");
//        }

        if (senderWallet.getBankAccount().getAccountNumber().equals(request.getRecipientAccountNumber())) {
            throw new TransactionException("Transfers to the same account are not allowed!");
        }

        Wallet recipientWallet = walletRepository.findByBankAccountAccountNumberAndCurrencyCode(request.getRecipientAccountNumber(), senderWallet.getCurrency().getCode());

        if (recipientWallet == null) {
            throw new WalletException("Wallet not found!");
        }

        User recipientUser = userRepository.findById(recipientWallet.getUser().getId()).orElseThrow(() -> new UserException("User not found!"));

        if (request.getAmountToTransfer().compareTo(senderWallet.getCurrency().getMinimumTransfer()) < 0) {
            throw new TransactionException("Amount is less than the minimum transfer!");
        }

        boolean checkPin = passwordEncoder.matches(request.getPin(), senderUser.getPin());

        if (!checkPin) {
            throw new UserException("Invalid pin!");
        }

        if (request.getAmountToTransfer().compareTo(senderWallet.getBalance()) > 0) {
            throw new TransactionException("Wallet balance insufficient!");
        }

        // sender wallet update balance (-)
        senderWallet.setBalance(senderWallet.getBalance().subtract(request.getAmountToTransfer()));
        senderWallet.setUpdatedAt(dateTimeService.getCurrentDateTimeInJakarta());
        walletRepository.save(senderWallet);

        // recipient wallet update balance (+)
        recipientWallet.setBalance(recipientWallet.getBalance().add(request.getAmountToTransfer()));
        recipientWallet.setUpdatedAt(dateTimeService.getCurrentDateTimeInJakarta());
        walletRepository.save(recipientWallet);

        // create financial trx for sender
        FinancialTrxRequestDTO senderFinancialTrxRequest = new FinancialTrxRequestDTO();
        senderFinancialTrxRequest.setUser(recipientUser);
        senderFinancialTrxRequest.setWallet(recipientWallet);
        senderFinancialTrxRequest.setTrxTypeName("Transfer");
        senderFinancialTrxRequest.setOperationTypeName("D");
        senderFinancialTrxRequest.setAccountNumber(request.getRecipientAccountNumber());
        senderFinancialTrxRequest.setAmount(request.getAmountToTransfer());
        FinancialTrxResponseDTO senderFinancialTrxResponse = financialTrxService.addFinancialTrx(senderFinancialTrxRequest);

        // create financial trx for recipient
        FinancialTrxRequestDTO recipientFinancialTrxRequest = new FinancialTrxRequestDTO();
        recipientFinancialTrxRequest.setUser(senderUser);
        recipientFinancialTrxRequest.setWallet(senderWallet);
        recipientFinancialTrxRequest.setTrxTypeName("Transfer");
        recipientFinancialTrxRequest.setOperationTypeName("K");
        recipientFinancialTrxRequest.setAccountNumber(senderWallet.getBankAccount().getAccountNumber());
        recipientFinancialTrxRequest.setAmount(request.getAmountToTransfer());
        FinancialTrxResponseDTO recipientFinancialTrxResponse = financialTrxService.addFinancialTrx(recipientFinancialTrxRequest);

        TransferValasResponseDTO response = new TransferValasResponseDTO();
        response.setAmountToTransfer(request.getAmountToTransfer());
        response.setCurrencyCode(senderWallet.getCurrency().getCode());
        response.setRecipientFirstName(recipientUser.getFirstName());
        response.setRecipientLastName(recipientUser.getLastName());
        response.setRecipientAccountNumber(request.getRecipientAccountNumber());
        response.setCreatedAt(String.valueOf(dateTimeService.getCurrentDateTimeInJakarta()));

        return response;
    }

    //////////////////////////////// VERSION 2.0 BLOCK ////////////////////////////////

    @Override
    public DetailTransferValasResponseDTO detailTransferValas(DetailTransferValasRequestDTO request, HttpServletRequest headerRequest) {

        // wallet request checking
        List<UUID> walletIds = resourceRequestCheckerService.walletChecker(headerRequest);
        if (!walletIds.contains(request.getSenderWalletId())) {
            throw new UserException("Un-match request!");
        }

        Wallet senderWallet = walletRepository.findById(request.getSenderWalletId()).orElseThrow(() -> new WalletException("Wallet not found!"));

        if (senderWallet.getBankAccount().getAccountNumber().equals(request.getRecipientAccountNumber())) {
            throw new TransactionException("Transfers to the same account are not allowed!");
        }

        Wallet recipientWallet = walletRepository.findByBankAccountAccountNumberAndCurrencyCode(request.getRecipientAccountNumber(), senderWallet.getCurrency().getCode());

        if (recipientWallet == null) {
            throw new WalletException("Wallet not found!");
        }

        if (request.getAmountToTransfer().compareTo(senderWallet.getCurrency().getMinimumTransfer()) < 0) {
            throw new TransactionException("Amount is less than the minimum transfer!");
        }

        DetailTransferValasResponseDTO response = new DetailTransferValasResponseDTO();
        response.setFirstName(recipientWallet.getUser().getFirstName());
        response.setLastName(recipientWallet.getUser().getLastName());
        response.setCurrencyCode(recipientWallet.getCurrency().getCode());
        response.setAmountToTransfer(request.getAmountToTransfer());

        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransferValasResponseDTO transferValas(TransferValasRequestDTO request, HttpServletRequest headerRequest) {

        // wallet request checking
        List<UUID> walletIds = resourceRequestCheckerService.walletChecker(headerRequest);
        if (!walletIds.contains(request.getSenderWalletId())) {
            throw new UserException("Un-match request!");
        }

        UUID userId = resourceRequestCheckerService.extractIdFromToken(headerRequest);

        Wallet senderWallet = walletRepository.findById(request.getSenderWalletId()).orElseThrow(() -> new WalletException("Wallet not found!"));
        User senderUser = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found!"));

        if (senderWallet.getBankAccount().getAccountNumber().equals(request.getRecipientAccountNumber())) {
            throw new TransactionException("Transfers to the same account are not allowed!");
        }

        Wallet recipientWallet = walletRepository.findByBankAccountAccountNumberAndCurrencyCode(request.getRecipientAccountNumber(), senderWallet.getCurrency().getCode());

        if (recipientWallet == null) {
            throw new WalletException("Wallet not found!");
        }

        User recipientUser = userRepository.findById(recipientWallet.getUser().getId()).orElseThrow(() -> new UserException("User not found!"));

        if (request.getAmountToTransfer().compareTo(senderWallet.getCurrency().getMinimumTransfer()) < 0) {
            throw new TransactionException("Amount is less than the minimum transfer!");
        }

        if (request.getAmountToTransfer().compareTo(senderWallet.getBalance()) > 0) {
            throw new TransactionException("Wallet balance insufficient!");
        }

        boolean checkPin = passwordEncoder.matches(request.getPin(), senderUser.getPin());
        if (!checkPin) {
            throw new UserException("Invalid pin!");
        }

        // sender wallet update balance (-)
        senderWallet.setBalance(senderWallet.getBalance().subtract(request.getAmountToTransfer()));
        senderWallet.setUpdatedAt(dateTimeService.getCurrentDateTimeInJakarta());
        walletRepository.save(senderWallet);

        // recipient wallet update balance (+)
        recipientWallet.setBalance(recipientWallet.getBalance().add(request.getAmountToTransfer()));
        recipientWallet.setUpdatedAt(dateTimeService.getCurrentDateTimeInJakarta());
        walletRepository.save(recipientWallet);

        // create financial trx for sender
        FinancialTrxRequestDTO senderFinancialTrxRequest = new FinancialTrxRequestDTO();
        senderFinancialTrxRequest.setUser(recipientUser);
        senderFinancialTrxRequest.setWallet(recipientWallet);
        senderFinancialTrxRequest.setTrxTypeName("Transfer");
        senderFinancialTrxRequest.setOperationTypeName("D");
        senderFinancialTrxRequest.setAccountNumber(request.getRecipientAccountNumber());
        senderFinancialTrxRequest.setAmount(request.getAmountToTransfer());
        FinancialTrxResponseDTO senderFinancialTrxResponse = financialTrxService.addFinancialTrx(senderFinancialTrxRequest);

        // create financial trx for recipient
        FinancialTrxRequestDTO recipientFinancialTrxRequest = new FinancialTrxRequestDTO();
        recipientFinancialTrxRequest.setUser(senderUser);
        recipientFinancialTrxRequest.setWallet(senderWallet);
        recipientFinancialTrxRequest.setTrxTypeName("Transfer");
        recipientFinancialTrxRequest.setOperationTypeName("K");
        recipientFinancialTrxRequest.setAccountNumber(senderWallet.getBankAccount().getAccountNumber());
        recipientFinancialTrxRequest.setAmount(request.getAmountToTransfer());
        FinancialTrxResponseDTO recipientFinancialTrxResponse = financialTrxService.addFinancialTrx(recipientFinancialTrxRequest);

        TransferValasResponseDTO response = new TransferValasResponseDTO();
        response.setAmountToTransfer(request.getAmountToTransfer());
        response.setCurrencyCode(senderWallet.getCurrency().getCode());
        response.setRecipientFirstName(recipientUser.getFirstName());
        response.setRecipientLastName(recipientUser.getLastName());
        response.setRecipientAccountNumber(request.getRecipientAccountNumber());
        response.setCreatedAt(String.valueOf(dateTimeService.getCurrentDateTimeInJakarta()));

        return response;
    }
}