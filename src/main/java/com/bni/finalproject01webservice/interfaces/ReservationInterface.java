package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.reservation_list.request.ReservationListRequestDTO;
import com.bni.finalproject01webservice.dto.reservation_list.request.UpdateReservationStatusRequestDTO;
import com.bni.finalproject01webservice.dto.reservation_list.response.ReservationListResponseDTO;
import com.bni.finalproject01webservice.dto.reservation_list.response.UpdateReservationStatusResponseDTO;
import com.bni.finalproject01webservice.dto.reservation_list.response.UserReservationListResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ReservationInterface {

    List<ReservationListResponseDTO> getAllReservation(ReservationListRequestDTO request);

    UpdateReservationStatusResponseDTO  updateReservationStatus(UpdateReservationStatusRequestDTO request, HttpServletRequest headerRequest);

    //////////////////////////////// VERSION 2.0 BLOCK ////////////////////////////////

    List<ReservationListResponseDTO> getAllReservation(ReservationListRequestDTO request, HttpServletRequest headerRequest);

    List<UserReservationListResponseDTO> getUserReservationList(HttpServletRequest headerRequest);
}