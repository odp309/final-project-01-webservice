package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.withdrawal_trx.request.WithdrawalTrxRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal_trx.response.WithdrawalTrxResponseDTO;
import com.bni.finalproject01webservice.interfaces.WithdrawalTrxInterface;
import com.bni.finalproject01webservice.model.OperationType;
import com.bni.finalproject01webservice.model.TrxType;
import com.bni.finalproject01webservice.model.WithdrawalTrx;
import com.bni.finalproject01webservice.repository.OperationTypeRepository;
import com.bni.finalproject01webservice.repository.TrxTypeRepository;
import com.bni.finalproject01webservice.repository.WithdrawalTrxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class WithdrawalTrxService implements WithdrawalTrxInterface {

    private final WithdrawalTrxRepository withdrawalTrxRepository;
    private final TrxTypeRepository trxTypeRepository;
    private final OperationTypeRepository operationTypeRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WithdrawalTrxResponseDTO addWithdrawalTrx(WithdrawalTrxRequestDTO request) {
        TrxType trxType = trxTypeRepository.findByName(request.getTrxTypeName());
        OperationType operationType = operationTypeRepository.findByName(request.getOperationTypeName());

        String reservationNumber = this.generateReservationNumber(request.getReservationDate(), request.getBranch().getCode());

        while (withdrawalTrxRepository.findByReservationNumber(reservationNumber) != null) {
            reservationNumber = this.generateReservationNumber(request.getReservationDate(), request.getBranch().getCode());
        }

        WithdrawalTrx withdrawalTrx = new WithdrawalTrx();
        withdrawalTrx.setAmount(request.getAmount());
        withdrawalTrx.setDetail(request.getTrxTypeName() + "/" +
                request.getAmount() + " " + request.getWallet().getCurrency().getCode() + "/" +
                request.getBranch().getType().split("/")[1] + " " + request.getBranch().getName() + "/" +
                reservationNumber);
        withdrawalTrx.setStatus(request.getStatus());
        withdrawalTrx.setReservationNumber(reservationNumber);
        withdrawalTrx.setReservationDate(request.getReservationDate());
        withdrawalTrx.setCreatedAt(new Date());
        withdrawalTrx.setUser(request.getUser());
        withdrawalTrx.setWallet(request.getWallet());
        withdrawalTrx.setBranch(request.getBranch());
        withdrawalTrx.setTrxType(trxType);
        withdrawalTrx.setOperationType(operationType);
        withdrawalTrxRepository.save(withdrawalTrx);

        WithdrawalTrxResponseDTO response = new WithdrawalTrxResponseDTO();
        response.setWithdrawalTrxId(withdrawalTrx.getId());
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