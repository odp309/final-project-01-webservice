package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.reservation_list.request.ReservationListRequestDTO;
import com.bni.finalproject01webservice.dto.reservation_list.response.ReservationListResponseDTO;
import com.bni.finalproject01webservice.dto.role.response.RoleResponseDTO;

import java.util.List;

public interface ReservationInterface {

    List<ReservationListResponseDTO> getAllReservation( ReservationListRequestDTO request);

}