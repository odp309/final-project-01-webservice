package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.employee.response.EmployeeResponseDTO;
import com.bni.finalproject01webservice.dto.reservation_list.request.ReservationListRequestDTO;
import com.bni.finalproject01webservice.dto.reservation_list.response.ReservationListResponseDTO;
import com.bni.finalproject01webservice.interfaces.ReservationInterface;
import com.bni.finalproject01webservice.model.Employee;
import com.bni.finalproject01webservice.model.WithdrawalTrx;
import com.bni.finalproject01webservice.repository.WithdrawalTrxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationListService implements ReservationInterface {

    private final WithdrawalTrxRepository withdrawalTrxRepository;

    @Override
    public List<ReservationListResponseDTO> getAllReservation(ReservationListRequestDTO request) {

        List<WithdrawalTrx> reserve = withdrawalTrxRepository.findByBranchName(request.getBrachName());

        return reserve.stream()
                .map(data -> {
                    ReservationListResponseDTO response = new ReservationListResponseDTO();
                    response.setReservationNumber(data.getReservationNumber());
                    response.setReservationDate(data.getReservationDate());
                    response.setCreatedDate(data.getCreatedAt());
                    response.setStatus(data.getStatus());
                    response.setAmount(data.getAmount());
                    response.setCurrencyCode(data.getWallet().getCurrency().getCode());
                    response.setCustomerName(data.getUser().getFirstName() + " " + data.getUser().getLastName());
                    response.setAccountNumber(data.getWallet().getBankAccount().getAccountNumber());

                    return response;
                })
                .collect(Collectors.toList());
    }
}
