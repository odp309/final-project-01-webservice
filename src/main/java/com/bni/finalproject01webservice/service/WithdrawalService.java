package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.withdrawal.request.ReportWithdrawalRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal.request.WithdrawalRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal.response.MonthDetailResponseDTO;
import com.bni.finalproject01webservice.dto.withdrawal.response.ReportWithdrawalResponseDTO;
import com.bni.finalproject01webservice.dto.withdrawal.response.RecapWithdrawalResponseDTO;
import com.bni.finalproject01webservice.dto.withdrawal.response.WithdrawalResponseDTO;
import com.bni.finalproject01webservice.interfaces.ResourceRequestCheckerInterface;
import com.bni.finalproject01webservice.interfaces.WithdrawalInterface;
import com.bni.finalproject01webservice.model.Withdrawal;
import com.bni.finalproject01webservice.repository.WithdrawalRepository;
import jakarta.servlet.http.HttpServletRequest;
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

    private final ResourceRequestCheckerInterface resourceRequestCheckerService;

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

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (RecapWithdrawalResponseDTO temp : report) {
            String tableDate = dateFormat.format(temp.getReservationDate());
            if (tableDate.startsWith(request.getYear() + "-")) {
                String month = tableDate.substring(5, 7);
                switch (month) {
                    case "01":
                        january.add(temp);
                        break;
                    case "02":
                        february.add(temp);
                        break;
                    case "03":
                        march.add(temp);
                        break;
                    case "04":
                        april.add(temp);
                        break;
                    case "05":
                        may.add(temp);
                        break;
                    case "06":
                        june.add(temp);
                        break;
                    case "07":
                        july.add(temp);
                        break;
                    case "08":
                        august.add(temp);
                        break;
                    case "09":
                        september.add(temp);
                        break;
                    case "10":
                        october.add(temp);
                        break;
                    case "11":
                        november.add(temp);
                        break;
                    case "12":
                        december.add(temp);
                        break;
                }
            }
        }

        List<MonthDetailResponseDTO> monthDetails = new ArrayList<>();
        monthDetails.add(new MonthDetailResponseDTO(january, february, march, april, may, june, july, august, september, october, november, december));

        return new ReportWithdrawalResponseDTO(monthDetails);
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

    //////////////////////////////// VERSION 2.0 BLOCK ////////////////////////////////

    @Override
    public ReportWithdrawalResponseDTO reportWithdrawal(ReportWithdrawalRequestDTO request, HttpServletRequest headerRequest) {

        String branchCode = resourceRequestCheckerService.extractBranchCodeFromToken(headerRequest);

        List<RecapWithdrawalResponseDTO> report = withdrawalRepository.getSumOfAmountGroupedByCurrencyAndMonth(branchCode);

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

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (RecapWithdrawalResponseDTO temp : report) {
            String tableDate = dateFormat.format(temp.getReservationDate());
            if (tableDate.startsWith(request.getYear() + "-")) {
                String month = tableDate.substring(5, 7);
                switch (month) {
                    case "01":
                        january.add(temp);
                        break;
                    case "02":
                        february.add(temp);
                        break;
                    case "03":
                        march.add(temp);
                        break;
                    case "04":
                        april.add(temp);
                        break;
                    case "05":
                        may.add(temp);
                        break;
                    case "06":
                        june.add(temp);
                        break;
                    case "07":
                        july.add(temp);
                        break;
                    case "08":
                        august.add(temp);
                        break;
                    case "09":
                        september.add(temp);
                        break;
                    case "10":
                        october.add(temp);
                        break;
                    case "11":
                        november.add(temp);
                        break;
                    case "12":
                        december.add(temp);
                        break;
                }
            }
        }

        List<MonthDetailResponseDTO> monthDetails = new ArrayList<>();
        monthDetails.add(new MonthDetailResponseDTO(january, february, march, april, may, june, july, august, september, october, november, december));

        return new ReportWithdrawalResponseDTO(monthDetails);
    }
}