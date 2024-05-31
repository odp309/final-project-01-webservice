package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.response.WalletResponseDTO;
import com.bni.finalproject01webservice.interfaces.WalletInterface;
import com.bni.finalproject01webservice.model.Wallet;
import com.bni.finalproject01webservice.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletService implements WalletInterface {
    @Autowired(required = true)
    private WalletRepository walletRepository;

    @Override
    public WalletResponseDTO getWallet(String accountNumber) {
        List<Wallet> wallets = walletRepository.findByBankAccountAccountNumber(accountNumber);
        if (wallets == null || wallets.isEmpty()) {
            throw new RuntimeException("wallet not found");
        }

        Wallet firstWallet = wallets.get(0);
        WalletResponseDTO response = new WalletResponseDTO();
        response.setAccountNumber(firstWallet.getBankAccount().getAccountNumber());
        //response.setId_user(firstWallet.getUser().get);

        List<WalletResponseDTO.WalletInfo> walletInfos = wallets.stream()
                .map(wallet -> {
                    WalletResponseDTO.WalletInfo walletInfo = new WalletResponseDTO.WalletInfo();
                    walletInfo.setCurrencyId(wallet.getCurrency().getCode());
                    walletInfo.setBalance(wallet.getBalance().floatValue());
                    return walletInfo;
                })
                .collect(Collectors.toList());

        response.setWallet(walletInfos);

        return response;
    }
}
