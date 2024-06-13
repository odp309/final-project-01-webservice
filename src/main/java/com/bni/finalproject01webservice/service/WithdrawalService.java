package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.withdrawal.request.ReportWithdrawalRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal.request.WithdrawalRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal.response.ReportWithdrawalResponseDTO;
import com.bni.finalproject01webservice.dto.withdrawal.response.RecapWithdrawalResponseDTO;
import com.bni.finalproject01webservice.dto.withdrawal.response.WithdrawalResponseDTO;
import com.bni.finalproject01webservice.interfaces.WithdrawalInterface;
import com.bni.finalproject01webservice.model.Withdrawal;
import com.bni.finalproject01webservice.repository.WithdrawalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WithdrawalService implements WithdrawalInterface {

    private final WithdrawalRepository withdrawalRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WithdrawalResponseDTO addWithdrawal(WithdrawalRequestDTO request) {
        String reservationCode = this.generateReservationCode(request.getReservationDate(), request.getBranch().getCode());

        while (withdrawalRepository.findByReservationCode(reservationCode) != null) {
            reservationCode = this.generateReservationCode(request.getReservationDate(), request.getBranch().getCode());
        }

        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(request.getAmount());
        withdrawal.setStatus(request.getStatus());
        withdrawal.setReservationCode(reservationCode);
        withdrawal.setReservationDate(request.getReservationDate());
        withdrawal.setCreatedAt(new Date());
        withdrawal.setUser(request.getUser());
        withdrawal.setWallet(request.getWallet());
        withdrawal.setBranch(request.getBranch());
        withdrawalRepository.save(withdrawal);

        WithdrawalResponseDTO response = new WithdrawalResponseDTO();
        response.setWithdrawal(withdrawal);

        return response;
    }

    @Override
    public ReportWithdrawalResponseDTO reportWithdrawal(ReportWithdrawalRequestDTO request) {

        List<RecapWithdrawalResponseDTO> report = withdrawalRepository.getSumOfAmountGroupedByCurrencyAndMonth(request.getBranchCode());

        List<RecapWithdrawalResponseDTO> january = new ArrayList<>();
        List<RecapWithdrawalResponseDTO> february = new ArrayList<>();
        List<RecapWithdrawalResponseDTO> march = new ArrayList<>();
        List<RecapWithdrawalResponseDTO> april = new ArrayList<>();
        List<RecapWithdrawalResponseDTO> may = new ArrayList<>();
        List<RecapWithdrawalResponseDTO> june = new ArrayList<>();
        List<RecapWithdrawalResponseDTO> july = new ArrayList<>();
        List<RecapWithdrawalResponseDTO> august = new ArrayList<>();
        List<RecapWithdrawalResponseDTO> september = new ArrayList<>();
        List<RecapWithdrawalResponseDTO> october = new ArrayList<>();
        List<RecapWithdrawalResponseDTO> november = new ArrayList<>();
        List<RecapWithdrawalResponseDTO> december = new ArrayList<>();

        for (RecapWithdrawalResponseDTO temp : report) {
            String tableDate = new SimpleDateFormat("yyyy-MM-dd").format(temp.getReservationDate());

            if (tableDate.equals(request.getYear() + "-" + "01" + "-" + "01")) {
                january.add(temp);
            } else if (tableDate.equals(request.getYear() + "-" + "02" + "-" + "01")) {
                february.add(temp);
            } else if (tableDate.equals(request.getYear() + "-" + "03" + "-" + "01")) {
                march.add(temp);
            } else if (tableDate.equals(request.getYear() + "-" + "04" + "-" + "01")) {
                april.add(temp);
            } else if (tableDate.equals(request.getYear() + "-" + "05" + "-" + "01")) {
                may.add(temp);
            } else if (tableDate.equals(request.getYear() + "-" + "06" + "-" + "01")) {
                june.add(temp);
            } else if (tableDate.equals(request.getYear() + "-" + "07" + "-" + "01")) {
                july.add(temp);
            } else if (tableDate.equals(request.getYear() + "-" + "08" + "-" + "01")) {
                august.add(temp);
            } else if (tableDate.equals(request.getYear() + "-" + "09" + "-" + "01")) {
                september.add(temp);
            } else if (tableDate.equals(request.getYear() + "-" + "10" + "-" + "01")) {
                october.add(temp);
            } else if (tableDate.equals(request.getYear() + "-" + "11" + "-" + "01")) {
                november.add(temp);
            } else if (tableDate.equals(request.getYear() + "-" + "12" + "-" + "01")) {
                december.add(temp);
            }
        }

        ReportWithdrawalResponseDTO response = new ReportWithdrawalResponseDTO();
        response.setMonthlyWithdrawalDetail("january", january);
        response.setMonthlyWithdrawalDetail("february", february);
        response.setMonthlyWithdrawalDetail("march", march);
        response.setMonthlyWithdrawalDetail("april", april);
        response.setMonthlyWithdrawalDetail("may", may);
        response.setMonthlyWithdrawalDetail("june", june);
        response.setMonthlyWithdrawalDetail("july", july);
        response.setMonthlyWithdrawalDetail("august", august);
        response.setMonthlyWithdrawalDetail("september", september);
        response.setMonthlyWithdrawalDetail("october", october);
        response.setMonthlyWithdrawalDetail("november", november);
        response.setMonthlyWithdrawalDetail("december", december);

        return response;
    }

    private String generateReservationCode(Date reservationDate, String branchCode) {
        Random random = new Random();
        int number = random.nextInt(10000); // Generate a number between 0 and 9999
        String randomNumber = String.format("%04d", number);

        LocalDate localDate = reservationDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();

        return "RES" +
                (day < 10 ? ("0" + day) : day) +
                (month < 10 ? ("0" + month) : month) +
                String.valueOf(year).substring(2) +
                branchCode +
                randomNumber;
    }
}