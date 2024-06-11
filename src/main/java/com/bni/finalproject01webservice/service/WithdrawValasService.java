package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.configuration.exceptions.TransactionException;
import com.bni.finalproject01webservice.configuration.exceptions.UserException;
import com.bni.finalproject01webservice.configuration.exceptions.WalletException;
import com.bni.finalproject01webservice.dto.withdraw_valas.request.DetailWithdrawValasRequestDTO;
import com.bni.finalproject01webservice.dto.withdraw_valas.request.WithdrawValasRequestDTO;
import com.bni.finalproject01webservice.dto.withdraw_valas.response.DetailWithdrawValasResponseDTO;
import com.bni.finalproject01webservice.dto.withdraw_valas.response.WithdrawValasResponseDTO;
import com.bni.finalproject01webservice.interfaces.TrxHistoryInterface;
import com.bni.finalproject01webservice.interfaces.WithdrawValasInterface;
import com.bni.finalproject01webservice.interfaces.WithdrawalTrxInterface;
import com.bni.finalproject01webservice.model.Branch;
import com.bni.finalproject01webservice.model.User;
import com.bni.finalproject01webservice.model.Wallet;
import com.bni.finalproject01webservice.repository.BankAccountRepository;
import com.bni.finalproject01webservice.repository.BranchRepository;
import com.bni.finalproject01webservice.repository.UserRepository;
import com.bni.finalproject01webservice.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WithdrawValasService implements WithdrawValasInterface {

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WalletRepository walletRepository;
    private final BranchRepository branchRepository;

    private final WithdrawalTrxInterface withdrawalTrxService;
    private final TrxHistoryInterface trxHistoryService;

    @Override
    public DetailWithdrawValasResponseDTO detailWithdrawValas(DetailWithdrawValasRequestDTO request) {
        Wallet wallet = walletRepository.findById(request.getWalletId()).orElseThrow(() -> new WalletException("Wallet not found!"));
        Branch branch = branchRepository.findById(request.getBranchCode()).orElseThrow(() -> new RuntimeException("Branch not found!"));

        if (request.getAmountToWithdraw().compareTo(wallet.getCurrency().getMinimumWithdrawal()) < 0) {
            throw new TransactionException("Amount is less than the minimum withdrawal!");
        } else if (request.getAmountToWithdraw().compareTo(wallet.getBalance()) > 0) {
            throw new TransactionException("Wallet balance insufficient!");
        }

        String[] branchType = branch.getType().split("/");

        DetailWithdrawValasResponseDTO response = new DetailWithdrawValasResponseDTO();
        response.setBranchName(branch.getName());
        response.setBranchTypeFull(branchType[0]);
        response.setBranchType(branchType[1]);
        response.setCurrencyCode(wallet.getCurrency().getCode());
        response.setAmountToWithdraw(request.getAmountToWithdraw());
        response.setReservationDate(request.getReservationDate());

        return response;
    }

    @Override
    public WithdrawValasResponseDTO withdrawValas(WithdrawValasRequestDTO request) {
        Wallet wallet = walletRepository.findById(request.getWalletId()).orElseThrow(() -> new WalletException("Wallet not found!"));
        User user = userRepository.findById(wallet.getUser().getId()).orElseThrow(() -> new UserException("User not found!"));

        if (request.getAmountToWithdraw().compareTo(wallet.getCurrency().getMinimumWithdrawal()) < 0) {
            throw new TransactionException("Amount is less than the minimum withdrawal!");
        }

        boolean checkPin = passwordEncoder.matches(request.getPin(), user.getPin());

        if (!checkPin) {
            throw new UserException("Invalid pin!");
        }

        if (request.getAmountToWithdraw().compareTo(wallet.getBalance()) > 0) {
            throw new TransactionException("Wallet balance insufficient!");
        }

        Branch branch = branchRepository.findById(request.getBranchCode()).orElseThrow(() -> new RuntimeException("Branch not found!"));

        return null;
    }
}
