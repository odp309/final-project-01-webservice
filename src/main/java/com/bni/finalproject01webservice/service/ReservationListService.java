package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.reservation_list.request.ReservationListRequestDTO;
import com.bni.finalproject01webservice.dto.reservation_list.request.UpdateReservationStatusRequestDTO;
import com.bni.finalproject01webservice.dto.reservation_list.response.ReservationListResponseDTO;
import com.bni.finalproject01webservice.dto.reservation_list.response.UpdateReservationStatusResponseDTO;
import com.bni.finalproject01webservice.interfaces.ReservationInterface;
import com.bni.finalproject01webservice.model.Withdrawal;
import com.bni.finalproject01webservice.repository.WithdrawalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationListService implements ReservationInterface {

    private final WithdrawalRepository withdrawalRepository;

    @Override
    public List<ReservationListResponseDTO> getAllReservation(ReservationListRequestDTO request) {

        List<Withdrawal> reserve = withdrawalRepository.findByBranchCode(request.getBranchCode());

        return reserve.stream()
                .map(data -> {
                    ReservationListResponseDTO response = new ReservationListResponseDTO();
                    response.setReservationNumber(data.getReservationCode());
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

    @Override
    public UpdateReservationStatusResponseDTO updateReservationStatus(UpdateReservationStatusRequestDTO request) {

       Withdrawal reservation = withdrawalRepository.findByReservationCode(request.getReservationNumber());
       UpdateReservationStatusResponseDTO response = new UpdateReservationStatusResponseDTO();

//        if (reservation.getReservationDate().compareTo(new Date()) > 0)
//        {
//            response.setUpdatedStatus("-");
//            response.setMessage("Your reservation number is no longer valid");
//            return response;
//        }

        if (reservation.getStatus().equalsIgnoreCase("Terjadwal"))
        {
            reservation.setStatus("Sukses");
            reservation.setUpdatedAt(new Date());
            //reservation.setDoneBy();
            withdrawalRepository.save(reservation);
            //harus tambah response lastupdatedBy siapa tapi nanti nunggu bagas ubah modelnya dulu
            //tambah juga nanti di responseDTO nya


            response.setUpdatedStatus("Sukses");
            response.setMessage("Withdrawal Transaction Succeed");
        }
        else
        {
            response.setUpdatedStatus("-");
            response.setMessage("Your reservation number is no longer valid");
        }

        return response;
    }
}
