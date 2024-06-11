package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.configuration.exceptions.TransactionException;
import com.bni.finalproject01webservice.configuration.exceptions.UserException;
import com.bni.finalproject01webservice.configuration.exceptions.WalletException;
import com.bni.finalproject01webservice.dto.trx_history.request.TrxHistoryRequestDTO;
import com.bni.finalproject01webservice.dto.trx_history.response.TrxHistoryResponseDTO;
import com.bni.finalproject01webservice.dto.withdraw_valas.request.DetailWithdrawValasRequestDTO;
import com.bni.finalproject01webservice.dto.withdraw_valas.request.WithdrawValasRequestDTO;
import com.bni.finalproject01webservice.dto.withdraw_valas.response.DetailWithdrawValasResponseDTO;
import com.bni.finalproject01webservice.dto.withdraw_valas.response.WithdrawValasResponseDTO;
import com.bni.finalproject01webservice.dto.withdrawal_trx.request.WithdrawalTrxRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal_trx.response.WithdrawalTrxResponseDTO;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
        Branch branch = branchRepository.findBranchWithValidation(request.getBranchCode(), request.getAmountToWithdraw(), wallet.getCurrency().getCode());

        if (branch == null) {
            throw new RuntimeException("Branch not found!");
        }

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
    @Transactional(rollbackFor = Exception.class)
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

        Branch branch = branchRepository.findBranchWithValidation(request.getBranchCode(), request.getAmountToWithdraw(), wallet.getCurrency().getCode());

        if (branch == null) {
            throw new RuntimeException("Branch not found!");
        }

        // wallet update balance (-)
        wallet.setBalance(wallet.getBalance().subtract(request.getAmountToWithdraw()));
        wallet.setUpdatedAt(new Date());
        walletRepository.save(wallet);

        // create withdrawal trx
        WithdrawalTrxRequestDTO withdrawalTrxRequest = new WithdrawalTrxRequestDTO();
        withdrawalTrxRequest.setUser(user);
        withdrawalTrxRequest.setWallet(wallet);
        withdrawalTrxRequest.setBranch(branch);
        withdrawalTrxRequest.setTrxTypeName("Tarik");
        withdrawalTrxRequest.setOperationTypeName("D");
        withdrawalTrxRequest.setStatus("Terjadwal");
        withdrawalTrxRequest.setAmount(request.getAmountToWithdraw());
        withdrawalTrxRequest.setReservationDate(request.getReservationDate());
        WithdrawalTrxResponseDTO withdrawalTrxResponse = withdrawalTrxService.addWithdrawalTrx(withdrawalTrxRequest);

        // create history trx
        TrxHistoryRequestDTO trxHistoryRequest = new TrxHistoryRequestDTO();
        trxHistoryRequest.setWithdrawalTrxId(withdrawalTrxResponse.getWithdrawalTrxId());
        TrxHistoryResponseDTO trxHistoryResponse = trxHistoryService.addTrxHistory(trxHistoryRequest);

        WithdrawValasResponseDTO response = new WithdrawValasResponseDTO();
        response.setAmountToWithdraw(request.getAmountToWithdraw());
        response.setBranchType(branch.getType().split("/")[1]);
        response.setBranchName(branch.getName());
        response.setBranchAddress(branch.getAddress());
        response.setBranchCity(branch.getCity());
        response.setBranchProvince(branch.getProvince());
        response.setCurrencyCode(wallet.getCurrency().getCode());
        response.setReservationNumber(withdrawalTrxResponse.getReservationNumber());
        response.setReservationDate(request.getReservationDate());
        response.setTrxHistory(trxHistoryResponse);

        return response;
    }
}