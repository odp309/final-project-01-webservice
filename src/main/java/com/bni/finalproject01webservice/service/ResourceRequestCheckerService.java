package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.interfaces.JWTInterface;
import com.bni.finalproject01webservice.interfaces.ResourceRequestCheckerInterface;
import com.bni.finalproject01webservice.model.BankAccount;
import com.bni.finalproject01webservice.model.FinancialTrx;
import com.bni.finalproject01webservice.model.Wallet;
import com.bni.finalproject01webservice.repository.BankAccountRepository;
import com.bni.finalproject01webservice.repository.FinancialTrxRepository;
import com.bni.finalproject01webservice.repository.WalletRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResourceRequestCheckerService implements ResourceRequestCheckerInterface {

    private final FinancialTrxRepository financialTrxRepository;
    private final WalletRepository walletRepository;
    private final BankAccountRepository bankAccountRepository;

    private final JWTInterface jwtService;

    @Override
    public List<UUID> financialTrxChecker(HttpServletRequest headerRequest) {

        UUID userId = this.extractIdFromToken(headerRequest);
        return financialTrxRepository
                .findByUserId(userId)
                .stream()
                .map(FinancialTrx::getId)
                .toList();
    }

    @Override
    public List<UUID> walletChecker(HttpServletRequest headerRequest) {

        UUID userId = this.extractIdFromToken(headerRequest);
        return walletRepository
                .findByUserId(userId)
                .stream()
                .map(Wallet::getId)
                .toList();
    }

    @Override
    public List<String> bankAccountChecker(HttpServletRequest headerRequest) {

        UUID userId = this.extractIdFromToken(headerRequest);
        return bankAccountRepository
                .findByUserId(userId)
                .stream()
                .map(BankAccount::getAccountNumber)
                .toList();
    }

    @Override
    public UUID extractIdFromToken(HttpServletRequest headerRequest) {
        return UUID.fromString(String.valueOf(jwtService.extractAllClaims(headerRequest.getHeader("Authorization").substring(7)).get("id")));
    }

    @Override
    public String extractBranchCodeFromToken(HttpServletRequest headerRequest) {
        return String.valueOf(jwtService.extractAllClaims(headerRequest.getHeader("Authorization").substring(7)).get("branchCode"));
    }
}
