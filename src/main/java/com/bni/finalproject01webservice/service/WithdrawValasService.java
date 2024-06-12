package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.configuration.exceptions.TransactionException;
import com.bni.finalproject01webservice.configuration.exceptions.UserException;
import com.bni.finalproject01webservice.configuration.exceptions.WalletException;
import com.bni.finalproject01webservice.configuration.exceptions.WithdrawalException;
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
import com.bni.finalproject01webservice.model.*;
import com.bni.finalproject01webservice.repository.*;
import com.bni.finalproject01webservice.utility.IndonesianHolidays;
import com.bni.finalproject01webservice.utility.WorkingDaysCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WithdrawValasService implements WithdrawValasInterface {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WalletRepository walletRepository;
    private final BranchRepository branchRepository;
    private final BranchReserveRepository branchReserveRepository;
    private final WithdrawalTrxRepository withdrawalTrxRepository;

    private final WithdrawalTrxInterface withdrawalTrxService;
    private final TrxHistoryInterface trxHistoryService;
    private final WorkingDaysCalculator workingDaysCalculator;

    private final Set<DayOfWeek> WEEKEND = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

    @Override
    public DetailWithdrawValasResponseDTO detailWithdrawValas(DetailWithdrawValasRequestDTO request) {

        LocalDateTime startDate = LocalDateTime.now();
        LocalDate endDate = request.getReservationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (WEEKEND.contains(endDate.getDayOfWeek())) {
            throw new WithdrawalException("The selected date is a weekend!");
        }
        if (IndonesianHolidays.isHoliday(endDate)) {
            throw new WithdrawalException("The selected date is a holiday!");
        }

        long workingDays = workingDaysCalculator.countWorkingDays(startDate, endDate);

        if (workingDays == 0) {
            throw new WithdrawalException("Workdays must be greater than zero!");
        } else if (workingDays > 5) {
            throw new WithdrawalException("Workdays cannot be larger than 5!");
        }

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

        LocalDateTime startDate = LocalDateTime.now();
        LocalDate endDate = request.getReservationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (WEEKEND.contains(endDate.getDayOfWeek())) {
            throw new WithdrawalException("The selected date is a weekend!");
        }
        if (IndonesianHolidays.isHoliday(endDate)) {
            throw new WithdrawalException("The selected date is a holiday!");
        }

        long workingDays = workingDaysCalculator.countWorkingDays(startDate, endDate);

        if (workingDays == 0) {
            throw new WithdrawalException("Workdays must be greater than zero!");
        } else if (workingDays > 5) {
            throw new WithdrawalException("Workdays cannot be larger than 5!");
        }

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

        BranchReserve branchReserve = branchReserveRepository.findByBranchCodeAndCurrencyCode(request.getBranchCode(), wallet.getCurrency().getCode());

        // wallet update balance (-)
        wallet.setBalance(wallet.getBalance().subtract(request.getAmountToWithdraw()));
        wallet.setUpdatedAt(new Date());
        walletRepository.save(wallet);

        // branch reserve update balance (-)
        branchReserve.setBalance(branchReserve.getBalance().subtract(request.getAmountToWithdraw()));
        branchReserve.setUpdatedAt(new Date());
        branchReserveRepository.save(branchReserve);

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

    @Override
    public void withdrawValasChecker() {
        LocalDate today = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        if (currentTime.isAfter(LocalTime.of(15, 0))) {
            List<WithdrawalTrx> scheduledWithdrawalTrx = withdrawalTrxRepository.findScheduledWithdrawalForToday(today);
            for (WithdrawalTrx withdrawalTrx : scheduledWithdrawalTrx) {
                withdrawalTrxRepository.updateWithdrawalTrxStatusToExpired(withdrawalTrx.getId());
            }
        }
    }
}