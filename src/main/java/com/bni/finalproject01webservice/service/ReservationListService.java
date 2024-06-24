package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.reservation_list.request.ReservationListRequestDTO;
import com.bni.finalproject01webservice.dto.reservation_list.request.UpdateReservationStatusRequestDTO;
import com.bni.finalproject01webservice.dto.reservation_list.response.ReservationListResponseDTO;
import com.bni.finalproject01webservice.dto.reservation_list.response.UpdateReservationStatusResponseDTO;
import com.bni.finalproject01webservice.dto.reservation_list.response.UserReservationListResponseDTO;
import com.bni.finalproject01webservice.interfaces.DateTimeInterface;
import com.bni.finalproject01webservice.interfaces.ReservationInterface;
import com.bni.finalproject01webservice.interfaces.ResourceRequestCheckerInterface;
import com.bni.finalproject01webservice.model.Withdrawal;
import com.bni.finalproject01webservice.repository.WithdrawalRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationListService implements ReservationInterface {

    private final WithdrawalRepository withdrawalRepository;

    private final ResourceRequestCheckerInterface resourceRequestCheckerService;
    private final DateTimeInterface dateTimeService;

    @Override
    public List<ReservationListResponseDTO> getAllReservation(ReservationListRequestDTO request) {

        List<Object[]> reserve = withdrawalRepository.findBySelectedBranchCode(request.getBranchCode());

        return reserve.stream()
                .map(data -> {
                    ReservationListResponseDTO response = new ReservationListResponseDTO();

                    response.setReservationNumber((String) data[0]); // assuming reservation number is at index 0
                    response.setReservationDate(String.valueOf(data[1])); // assuming reservation date is at index 1
                    response.setCreatedDate(String.valueOf(data[2])); // assuming created date is at index 2
                    response.setStatus((String) data[3]); // assuming status is at index 3
                    response.setAmount((BigDecimal) data[4]); // assuming amount is at index 4
                    response.setCurrencyCode((String) data[5]); // assuming currency code is at index 5
                    response.setAccountNumber((String) data[6]);
                    response.setCustomerName((String) data[9] + " " + (String) data[10]); // assuming first name is at index 6 and last name is at index 7
                    response.setDoneBy((String) data[7]+ " " + (String) data[8]);
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public UpdateReservationStatusResponseDTO updateReservationStatus(UpdateReservationStatusRequestDTO request, HttpServletRequest headerRequest) {

        UUID employeeId = resourceRequestCheckerService.extractIdFromToken(headerRequest);

        Withdrawal reservation = withdrawalRepository.findByReservationCode(request.getReservationNumber());


        UpdateReservationStatusResponseDTO response = new UpdateReservationStatusResponseDTO();

        if (reservation.getStatus().equalsIgnoreCase("Terjadwal")) {
            reservation.setStatus("Sukses");
            reservation.setUpdatedAt(dateTimeService.getCurrentDateTimeInJakarta());
            reservation.setDoneBy(employeeId.toString());
            withdrawalRepository.save(reservation);

            response.setUpdatedStatus("Sukses");
            response.setMessage("Withdrawal Transaction Succeed");
        } else {
            response.setUpdatedStatus("-");
            response.setMessage("Your reservation number is no longer valid");
        }

        return response;
    }

    //////////////////////////////// VERSION 2.0 BLOCK ////////////////////////////////

    @Override
    public List<ReservationListResponseDTO> getAllReservation(ReservationListRequestDTO request, HttpServletRequest headerRequest) {

        String branchCode = resourceRequestCheckerService.extractBranchCodeFromToken(headerRequest);

        List<Object[]> reserve = withdrawalRepository.findBySelectedBranchCode(branchCode);

        return reserve.stream()
                .map(data -> {
                    ReservationListResponseDTO response = new ReservationListResponseDTO();

                    response.setReservationNumber((String) data[0]);
                    response.setReservationDate(String.valueOf(data[1]));
                    response.setCreatedDate(String.valueOf(data[2]));
                    response.setStatus((String) data[3]);
                    response.setAmount((BigDecimal) data[4]);
                    response.setCurrencyCode((String) data[5]);
                    response.setAccountNumber((String) data[6]);
                    response.setCustomerName((String) data[9] + " " + (String) data[10]); // assuming first name is at index 6 and last name is at index 7
                    response.setDoneBy((String) data[7]+ " " + (String) data[8]);
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<UserReservationListResponseDTO> getUserReservationList(HttpServletRequest headerRequest) {

        UUID userId = resourceRequestCheckerService.extractIdFromToken(headerRequest);

        List<Withdrawal> reservation = withdrawalRepository.findBySelectedId(userId);

        return reservation.stream()
                .map(data -> {
                    UserReservationListResponseDTO response = new UserReservationListResponseDTO();

                    response.setReservationNumber(data.getReservationCode());
                    response.setReservationDate(String.valueOf(data.getReservationDate()));
                    response.setAmount(data.getAmount());
                    response.setCurrencyCode(data.getWallet().getCurrency().getCode());
                    response.setStatus(data.getStatus());
                    response.setBranchType(data.getBranch().getType());
                    response.setBranchName(data.getBranch().getName());
                    response.setBranchAddress(data.getBranch().getAddress());

                    return response;
                })
                .collect(Collectors.toList());
    }
}