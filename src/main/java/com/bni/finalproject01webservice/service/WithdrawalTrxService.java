package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.configuration.exceptions.TransactionException;
import com.bni.finalproject01webservice.configuration.exceptions.WalletException;
import com.bni.finalproject01webservice.dto.withdrawal_trx.request.DetailWithdrawalTrxRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal_trx.request.WithdrawalTrxRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal_trx.response.DetailWithdrawalTrxResponseDTO;
import com.bni.finalproject01webservice.dto.withdrawal_trx.response.WithdrawalTrxResponseDTO;
import com.bni.finalproject01webservice.interfaces.FinancialTrxInterface;
import com.bni.finalproject01webservice.interfaces.TrxHistoryInterface;
import com.bni.finalproject01webservice.interfaces.WithdrawalTrxInterface;
import com.bni.finalproject01webservice.model.Branch;
import com.bni.finalproject01webservice.model.Wallet;
import com.bni.finalproject01webservice.repository.BankAccountRepository;
import com.bni.finalproject01webservice.repository.BranchRepository;
import com.bni.finalproject01webservice.repository.UserRepository;
import com.bni.finalproject01webservice.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WithdrawalTrxService implements WithdrawalTrxInterface {

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WalletRepository walletRepository;
    private final BranchRepository branchRepository;

    private final FinancialTrxInterface financialTrxService;
    private final TrxHistoryInterface trxHistoryService;

    @Override
    public DetailWithdrawalTrxResponseDTO detailWithdrawalTrx(DetailWithdrawalTrxRequestDTO request) {
        Wallet wallet = walletRepository.findById(request.getWalletId()).orElseThrow(() -> new WalletException("Wallet not found!"));
        Branch branch = branchRepository.findById(request.getBranchId()).orElseThrow(() -> new RuntimeException("Branch not found!"));

        if (request.getAmountToWithdraw().compareTo(wallet.getCurrency().getMinimumWithdrawal()) < 0) {
            throw new TransactionException("Amount is less than the minimum withdrawal!");
        } else if (request.getAmountToWithdraw().compareTo(wallet.getBalance()) > 0) {
            throw new TransactionException("Wallet balance insufficient!");
        }

        String[] branchType = branch.getType().split("/");

        DetailWithdrawalTrxResponseDTO response = new DetailWithdrawalTrxResponseDTO();
        response.setBranchName(branch.getName());
        response.setBranchTypeFull(branchType[0]);
        response.setBranchType(branchType[1]);
        response.setCurrencyCode(wallet.getCurrency().getCode());
        response.setAmountToWithdraw(request.getAmountToWithdraw());
        response.setReservationDate(request.getReservationDate());

        return response;
    }

    @Override
    public WithdrawalTrxResponseDTO withdrawalTrx(WithdrawalTrxRequestDTO request) {
//        Wallet wallet = walletRepository.findById(request.getWalletId()).orElseThrow(() -> new WalletException("Wallet not found!"));
//        Branch branch = branchRepository.findById(request.getBranchId()).orElseThrow(() -> new RuntimeException("Branch not found!"));
//
//        if (request.getAmountToWithdraw().compareTo(wallet.getCurrency().getMinimumWithdrawal()) < 0) {
//            throw new TransactionException("Amount is less than the minimum withdrawal!");
//        } else if (request.getAmountToWithdraw().compareTo(wallet.getBalance()) > 0) {
//            throw new TransactionException("Wallet balance insufficient!");
//        }

        return null;
    }
}
