package com.bni.finalproject01webservice.controller.private_api.v1;


import com.bni.finalproject01webservice.dto.reservation_list.request.ReservationListRequestDTO;
import com.bni.finalproject01webservice.dto.reservation_list.request.UpdateReservationStatusRequestDTO;
import com.bni.finalproject01webservice.dto.reservation_list.response.ReservationListResponseDTO;
import com.bni.finalproject01webservice.dto.reservation_list.response.UpdateReservationStatusResponseDTO;
import com.bni.finalproject01webservice.interfaces.ReservationInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/private/reservation-list")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API", description = "Private API secured with JWT token")
public class ReservationListController {

    @Autowired
    private ReservationInterface reservationService;

    @PostMapping("/get")
    public List<ReservationListResponseDTO> getAllReservationList(@RequestBody ReservationListRequestDTO request) {
        return reservationService.getAllReservation(request);
    }

    @PostMapping("/update-status")
    public UpdateReservationStatusResponseDTO updateReservationUpdate(@RequestBody UpdateReservationStatusRequestDTO request, HttpServletRequest headerRequest) {
        return reservationService.updateReservationStatus(request, headerRequest);
    }

}
