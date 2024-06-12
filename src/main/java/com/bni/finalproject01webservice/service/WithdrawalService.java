package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.withdrawal.request.WithdrawalRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal.response.WithdrawalResponseDTO;
import com.bni.finalproject01webservice.interfaces.WithdrawalInterface;
import com.bni.finalproject01webservice.model.Withdrawal;
import com.bni.finalproject01webservice.repository.WithdrawalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

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