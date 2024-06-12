package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.withdrawal.request.WithdrawalRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal.response.WithdrawalResponseDTO;
import com.bni.finalproject01webservice.interfaces.WithdrawalInterface;
import com.bni.finalproject01webservice.model.OperationType;
import com.bni.finalproject01webservice.model.TrxType;
import com.bni.finalproject01webservice.model.Withdrawal;
import com.bni.finalproject01webservice.repository.OperationTypeRepository;
import com.bni.finalproject01webservice.repository.TrxTypeRepository;
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
    private final TrxTypeRepository trxTypeRepository;
    private final OperationTypeRepository operationTypeRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WithdrawalResponseDTO addWithdrawal(WithdrawalRequestDTO request) {
        TrxType trxType = trxTypeRepository.findByName(request.getTrxTypeName());
        OperationType operationType = operationTypeRepository.findByName(request.getOperationTypeName());

        String reservationNumber = this.generateReservationNumber(request.getReservationDate(), request.getBranch().getCode());

        while (withdrawalRepository.findByReservationNumber(reservationNumber) != null) {
            reservationNumber = this.generateReservationNumber(request.getReservationDate(), request.getBranch().getCode());
        }

        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(request.getAmount());
//        withdrawal.setDetail(request.getTrxTypeName() + "/" +
//                request.getAmount() + " " + request.getWallet().getCurrency().getCode() + "/" +
//                request.getBranch().getType().split("/")[1] + " " + request.getBranch().getName() + "/" +
//                reservationNumber);
        withdrawal.setStatus(request.getStatus());
        withdrawal.setReservationNumber(reservationNumber);
        withdrawal.setReservationDate(request.getReservationDate());
        withdrawal.setCreatedAt(new Date());
        withdrawal.setUser(request.getUser());
        withdrawal.setWallet(request.getWallet());
        withdrawal.setBranch(request.getBranch());
//        withdrawal.setTrxType(trxType);
//        withdrawal.setOperationType(operationType);
        withdrawalRepository.save(withdrawal);

        WithdrawalResponseDTO response = new WithdrawalResponseDTO();
        response.setWithdrawalId(withdrawal.getId());
        response.setReservationNumber(reservationNumber);

        return response;
    }

    private String generateReservationNumber(Date reservationDate, String branchCode) {
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