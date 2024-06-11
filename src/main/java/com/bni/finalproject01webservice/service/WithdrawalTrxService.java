package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.withdrawal_trx.request.WithdrawalTrxRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal_trx.response.WithdrawalTrxResponseDTO;
import com.bni.finalproject01webservice.interfaces.WithdrawalTrxInterface;
import com.bni.finalproject01webservice.model.BankAccount;
import com.bni.finalproject01webservice.model.OperationType;
import com.bni.finalproject01webservice.model.TrxType;
import com.bni.finalproject01webservice.model.WithdrawalTrx;
import com.bni.finalproject01webservice.repository.BankAccountRepository;
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
    private final BankAccountRepository bankAccountRepository;
    private final TrxTypeRepository trxTypeRepository;
    private final OperationTypeRepository operationTypeRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WithdrawalTrxResponseDTO addWithdrawalTrx(WithdrawalTrxRequestDTO request) {
        TrxType trxType = trxTypeRepository.findByName(request.getTrxTypeName());
        OperationType operationType = operationTypeRepository.findByName(request.getOperationTypeName());

        WithdrawalTrx withdrawalTrx = new WithdrawalTrx();
        withdrawalTrx.setAmount(request.getAmount());
        withdrawalTrx.setStatus(request.getStatus());
        withdrawalTrx.setReservationDate(request.getReservationDate());

        return null;
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

        return "RES" + day + month + String.valueOf(year).substring(2) + branchCode + randomNumber;
    }
}