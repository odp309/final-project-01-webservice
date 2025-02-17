package com.bni.finalproject01webservice.controller.private_api.v2;

import com.bni.finalproject01webservice.dto.reservation_list.request.ReservationListRequestDTO;
import com.bni.finalproject01webservice.dto.reservation_list.request.UpdateReservationStatusRequestDTO;
import com.bni.finalproject01webservice.dto.reservation_list.response.ReservationListResponseDTO;
import com.bni.finalproject01webservice.dto.reservation_list.response.UpdateReservationStatusResponseDTO;
import com.bni.finalproject01webservice.dto.reservation_list.response.UserReservationListResponseDTO;
import com.bni.finalproject01webservice.interfaces.ReservationInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/private/reservation-list")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API V2", description = "Private API secured with JWT token")
public class ReservationListControllerV2 {

    @Autowired
    private ReservationInterface reservationService;

    @PostMapping("/get")
    public List<ReservationListResponseDTO> getAllReservationList(@RequestBody ReservationListRequestDTO request, HttpServletRequest headerRequest) {
        return reservationService.getAllReservation(request, headerRequest);
    }

    @PostMapping("/update-status")
    public UpdateReservationStatusResponseDTO updateReservationUpdate(@RequestBody UpdateReservationStatusRequestDTO request, HttpServletRequest headerRequest) {
        return reservationService.updateReservationStatus(request, headerRequest);
    }

    @GetMapping("/user-get")
    public List<UserReservationListResponseDTO> getUserReservationList(HttpServletRequest headerRequest) {
        return reservationService.getUserReservationList(headerRequest);
    }
}
