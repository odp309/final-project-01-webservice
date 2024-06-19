package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.configuration.exceptions.TransactionException;
import com.bni.finalproject01webservice.configuration.exceptions.UserException;
import com.bni.finalproject01webservice.configuration.exceptions.WalletException;
import com.bni.finalproject01webservice.configuration.exceptions.WithdrawalException;
import com.bni.finalproject01webservice.dto.branch_reserve_log.request.BranchReserveLogRequestDTO;
import com.bni.finalproject01webservice.dto.branch_reserve_log.response.BranchReserveLogResponseDTO;
import com.bni.finalproject01webservice.dto.withdraw_valas.request.DetailWithdrawValasRequestDTO;
import com.bni.finalproject01webservice.dto.withdraw_valas.request.WithdrawValasRequestDTO;
import com.bni.finalproject01webservice.dto.withdraw_valas.response.DetailWithdrawValasResponseDTO;
import com.bni.finalproject01webservice.dto.withdraw_valas.response.WithdrawValasResponseDTO;
import com.bni.finalproject01webservice.dto.withdrawal.request.WithdrawalRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal.response.WithdrawalResponseDTO;
import com.bni.finalproject01webservice.dto.withdrawal_detail.request.WithdrawalDetailRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal_detail.response.WithdrawalDetailResponseDTO;
import com.bni.finalproject01webservice.interfaces.*;
import com.bni.finalproject01webservice.model.*;
import com.bni.finalproject01webservice.repository.*;
import com.bni.finalproject01webservice.utility.IndonesianHolidays;
import com.bni.finalproject01webservice.utility.WorkingDaysCalculator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WithdrawValasService implements WithdrawValasInterface {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WalletRepository walletRepository;
    private final BranchRepository branchRepository;
    private final BranchReserveRepository branchReserveRepository;
    private final WithdrawalRepository withdrawalRepository;

    private final WithdrawalInterface withdrawalService;
    private final WithdrawalDetailInterface withdrawalDetailService;
    private final BranchReserveLogInterface branchReserveLogService;
    private final WorkingDaysCalculator workingDaysCalculator;
    private final ResourceRequestCheckerInterface resourceRequestCheckerService;

    private final Set<DayOfWeek> WEEKEND = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

    @Override
    public DetailWithdrawValasResponseDTO detailWithdrawValas(DetailWithdrawValasRequestDTO request) {

        Wallet wallet = walletRepository.findById(request.getWalletId()).orElseThrow(() -> new WalletException("Wallet not found!"));

        // branch checking
        Branch branch = branchRepository.findBranchWithValidation(request.getBranchCode(), request.getAmountToWithdraw(), wallet.getCurrency().getCode());
        if (branch == null) {
            throw new RuntimeException("Branch not found!");
        }

        // user cooldown checking
        User user = userRepository.findById(wallet.getUser().getId()).orElseThrow(() -> new UserException("User not found!"));
        if (user.getIsCooldown()) {
            throw new WithdrawalException("User is in cooldown!");
        }

        // withdrawal one at a time checking
        Withdrawal withdraw = withdrawalRepository.findByUserIdAndStatus(user.getId(), "Terjadwal");
        if (withdraw != null) {
            throw new WithdrawalException("User already had ongoing withdrawal!");
        }

        LocalDateTime startDate = LocalDateTime.now();
        LocalDate endDate = request.getReservationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (WEEKEND.contains(endDate.getDayOfWeek())) {
            throw new WithdrawalException("The selected date is a weekend!");
        }

        if (IndonesianHolidays.isHoliday(endDate)) {
            throw new WithdrawalException("The selected date is a holiday!");
        }

        // working days only 1 - 5
        long workingDays = workingDaysCalculator.countWorkingDays(startDate, endDate);
        if (workingDays == 0) {
            throw new WithdrawalException("Workdays must be greater than zero!");
        } else if (workingDays > 5) {
            throw new WithdrawalException("Workdays cannot be larger than 5!");
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

        if (user.getIsCooldown()) {
            throw new WithdrawalException("User is in cooldown!");
        }

        Withdrawal withdraw = withdrawalRepository.findByUserIdAndStatus(user.getId(), "Terjadwal");
        if (withdraw != null) {
            throw new WithdrawalException("User already had ongoing withdrawal!");
        }

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

        BigDecimal currentBranchReserveBalance = branchReserve.getBalance();
        BigDecimal updatedBranchReserveBalance = branchReserve.getBalance().subtract(request.getAmountToWithdraw());

        // wallet update balance (-)
        wallet.setBalance(wallet.getBalance().subtract(request.getAmountToWithdraw()));
        wallet.setUpdatedAt(new Date());
        walletRepository.save(wallet);

        // branch reserve update balance (-)
        branchReserve.setBalance(updatedBranchReserveBalance);
        branchReserve.setUpdatedAt(new Date());
        branchReserveRepository.save(branchReserve);

        // create withdrawal
        WithdrawalRequestDTO withdrawalRequest = new WithdrawalRequestDTO();
        withdrawalRequest.setUser(user);
        withdrawalRequest.setWallet(wallet);
        withdrawalRequest.setBranch(branch);
        withdrawalRequest.setStatus("Terjadwal");
        withdrawalRequest.setAmount(request.getAmountToWithdraw());
        withdrawalRequest.setReservationDate(request.getReservationDate());
        WithdrawalResponseDTO withdrawalResponse = withdrawalService.addWithdrawal(withdrawalRequest);

        // create withdrawal detail
        WithdrawalDetailRequestDTO withdrawalDetailRequest = new WithdrawalDetailRequestDTO();
        withdrawalDetailRequest.setWithdrawal(withdrawalResponse.getWithdrawal());
        withdrawalDetailRequest.setTrxTypeName("Tarik");
        withdrawalDetailRequest.setOperationTypeName("D");
        WithdrawalDetailResponseDTO withdrawalDetailResponse = withdrawalDetailService.addWithdrawalDetail(withdrawalDetailRequest);

        // create branch reserve log
        BranchReserveLogRequestDTO logRequest = new BranchReserveLogRequestDTO();
        logRequest.setAmount(request.getAmountToWithdraw());
        logRequest.setCurrentBalance(currentBranchReserveBalance);
        logRequest.setUpdatedBalance(updatedBranchReserveBalance);
        logRequest.setUpdatedBy("SYSTEM");
        logRequest.setOperationTypeName("D");
        logRequest.setBranchReserve(branchReserve);
        BranchReserveLogResponseDTO logResponse = branchReserveLogService.addBranchReserveLog(logRequest);

        WithdrawValasResponseDTO response = new WithdrawValasResponseDTO();
        response.setAmountToWithdraw(request.getAmountToWithdraw());
        response.setBranchType(branch.getType().split("/")[1]);
        response.setBranchName(branch.getName());
        response.setBranchAddress(branch.getAddress());
        response.setBranchCity(branch.getCity());
        response.setBranchProvince(branch.getProvince());
        response.setCurrencyCode(wallet.getCurrency().getCode());
        response.setReservationCode(withdrawalResponse.getWithdrawal().getReservationCode());
        response.setReservationDate(request.getReservationDate());

        return response;
    }

    @Override
    public void withdrawValasScheduledChecker() {
        LocalDate today = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        if (currentTime.isAfter(LocalTime.of(22, 0))) {
//            if (currentTime.isAfter(LocalTime.of(15, 0))) {
            List<Withdrawal> scheduledWithdrawals = withdrawalRepository.findScheduledWithdrawalForToday(today);
            for (Withdrawal withdrawal : scheduledWithdrawals) {
                BranchReserve branchReserve = branchReserveRepository.findByBranchCodeAndCurrencyCode(withdrawal.getBranch().getCode(), withdrawal.getWallet().getCurrency().getCode());

                // create withdrawal detail
                WithdrawalDetailRequestDTO withdrawalDetailRequest = new WithdrawalDetailRequestDTO();
                withdrawalDetailRequest.setWithdrawal(withdrawal);
                withdrawalDetailRequest.setTrxTypeName("Pengembalian Dana");
                withdrawalDetailRequest.setOperationTypeName("K");
                WithdrawalDetailResponseDTO withdrawalDetailResponse = withdrawalDetailService.addWithdrawalDetail(withdrawalDetailRequest);

                // create branch reserve log
                BranchReserveLogRequestDTO logRequest = new BranchReserveLogRequestDTO();
                logRequest.setAmount(withdrawal.getAmount());
                logRequest.setCurrentBalance(branchReserve.getBalance());
                logRequest.setUpdatedBalance(branchReserve.getBalance().add(withdrawal.getAmount()));
                logRequest.setUpdatedBy("SYSTEM");
                logRequest.setOperationTypeName("K");
                logRequest.setBranchReserve(branchReserve);
                BranchReserveLogResponseDTO logResponse = branchReserveLogService.addBranchReserveLog(logRequest);

                withdrawalRepository.updateWithdrawalStatusToExpired(withdrawal.getId());
                userRepository.updateIsCooldownToTrue(withdrawal.getUser().getId());
                walletRepository.updateWalletBalance(withdrawal.getWallet().getId(), withdrawal.getWallet().getBalance().add(withdrawal.getAmount()));
                branchReserveRepository.updateBranchReserveBalance(branchReserve.getId(), branchReserve.getBalance().add(withdrawal.getAmount()));
            }
        }
    }

    //////////////////////////////// VERSION 2.0 BLOCK ////////////////////////////////

    @Override
    public DetailWithdrawValasResponseDTO detailWithdrawValas(DetailWithdrawValasRequestDTO request, HttpServletRequest headerRequest) {

        // wallet request checking
        List<UUID> walletIds = resourceRequestCheckerService.walletChecker(headerRequest);
        if (!walletIds.contains(request.getWalletId())) {
            throw new UserException("Un-match request!");
        }

        UUID userId = resourceRequestCheckerService.extractIdFromToken(headerRequest);

        Wallet wallet = walletRepository.findById(request.getWalletId()).orElseThrow(() -> new WalletException("Wallet not found!"));

        // branch checking
        Branch branch = branchRepository.findBranchWithValidation(request.getBranchCode(), request.getAmountToWithdraw(), wallet.getCurrency().getCode());
        if (branch == null) {
            throw new RuntimeException("Branch not found!");
        }

        // user cooldown checking
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found!"));
        if (user.getIsCooldown()) {
            throw new WithdrawalException("User is in cooldown!");
        }

        // withdrawal one at a time checking
        Withdrawal withdraw = withdrawalRepository.findByUserIdAndStatus(user.getId(), "Terjadwal");
        if (withdraw != null) {
            throw new WithdrawalException("User already had ongoing withdrawal!");
        }

        LocalDateTime startDate = LocalDateTime.now();
        LocalDate endDate = request.getReservationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (WEEKEND.contains(endDate.getDayOfWeek())) {
            throw new WithdrawalException("The selected date is a weekend!");
        }

        if (IndonesianHolidays.isHoliday(endDate)) {
            throw new WithdrawalException("The selected date is a holiday!");
        }

        // working days only 1 - 5
        long workingDays = workingDaysCalculator.countWorkingDays(startDate, endDate);
        if (workingDays == 0) {
            throw new WithdrawalException("Workdays must be greater than zero!");
        } else if (workingDays > 5) {
            throw new WithdrawalException("Workdays cannot be larger than 5!");
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
    public WithdrawValasResponseDTO withdrawValas(WithdrawValasRequestDTO request, HttpServletRequest headerRequest) {

        // wallet request checking
        List<UUID> walletIds = resourceRequestCheckerService.walletChecker(headerRequest);
        if (!walletIds.contains(request.getWalletId())) {
            throw new UserException("Un-match request!");
        }

        UUID userId = resourceRequestCheckerService.extractIdFromToken(headerRequest);

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
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found!"));

        if (user.getIsCooldown()) {
            throw new WithdrawalException("User is in cooldown!");
        }

        Withdrawal withdraw = withdrawalRepository.findByUserIdAndStatus(user.getId(), "Terjadwal");
        if (withdraw != null) {
            throw new WithdrawalException("User already had ongoing withdrawal!");
        }

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

        BigDecimal currentBranchReserveBalance = branchReserve.getBalance();
        BigDecimal updatedBranchReserveBalance = branchReserve.getBalance().subtract(request.getAmountToWithdraw());

        // wallet update balance (-)
        wallet.setBalance(wallet.getBalance().subtract(request.getAmountToWithdraw()));
        wallet.setUpdatedAt(new Date());
        walletRepository.save(wallet);

        // branch reserve update balance (-)
        branchReserve.setBalance(updatedBranchReserveBalance);
        branchReserve.setUpdatedAt(new Date());
        branchReserveRepository.save(branchReserve);

        // create withdrawal
        WithdrawalRequestDTO withdrawalRequest = new WithdrawalRequestDTO();
        withdrawalRequest.setUser(user);
        withdrawalRequest.setWallet(wallet);
        withdrawalRequest.setBranch(branch);
        withdrawalRequest.setStatus("Terjadwal");
        withdrawalRequest.setAmount(request.getAmountToWithdraw());
        withdrawalRequest.setReservationDate(request.getReservationDate());
        WithdrawalResponseDTO withdrawalResponse = withdrawalService.addWithdrawal(withdrawalRequest);

        // create withdrawal detail
        WithdrawalDetailRequestDTO withdrawalDetailRequest = new WithdrawalDetailRequestDTO();
        withdrawalDetailRequest.setWithdrawal(withdrawalResponse.getWithdrawal());
        withdrawalDetailRequest.setTrxTypeName("Tarik");
        withdrawalDetailRequest.setOperationTypeName("D");
        WithdrawalDetailResponseDTO withdrawalDetailResponse = withdrawalDetailService.addWithdrawalDetail(withdrawalDetailRequest);

        // create branch reserve log
        BranchReserveLogRequestDTO logRequest = new BranchReserveLogRequestDTO();
        logRequest.setAmount(request.getAmountToWithdraw());
        logRequest.setCurrentBalance(currentBranchReserveBalance);
        logRequest.setUpdatedBalance(updatedBranchReserveBalance);
        logRequest.setUpdatedBy("SYSTEM");
        logRequest.setOperationTypeName("D");
        logRequest.setBranchReserve(branchReserve);
        BranchReserveLogResponseDTO logResponse = branchReserveLogService.addBranchReserveLog(logRequest);

        WithdrawValasResponseDTO response = new WithdrawValasResponseDTO();
        response.setAmountToWithdraw(request.getAmountToWithdraw());
        response.setBranchType(branch.getType().split("/")[1]);
        response.setBranchName(branch.getName());
        response.setBranchAddress(branch.getAddress());
        response.setBranchCity(branch.getCity());
        response.setBranchProvince(branch.getProvince());
        response.setCurrencyCode(wallet.getCurrency().getCode());
        response.setReservationCode(withdrawalResponse.getWithdrawal().getReservationCode());
        response.setReservationDate(request.getReservationDate());

        return response;
    }
}