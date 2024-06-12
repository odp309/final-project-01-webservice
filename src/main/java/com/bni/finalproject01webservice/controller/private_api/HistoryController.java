package com.bni.finalproject01webservice.controller.private_api;

import com.bni.finalproject01webservice.dto.auth.response.RegisterResponseDTO;
import com.bni.finalproject01webservice.dto.employee.request.RegisterEmployeeRequestDTO;
import com.bni.finalproject01webservice.dto.history.request.HistoryDataRequestDTO;
import com.bni.finalproject01webservice.dto.history.response.HistoryDataResponseDTO;
import com.bni.finalproject01webservice.interfaces.HistoryInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/private/history")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API", description = "Private API secured with JWT token")
public class HistoryController {

    @Autowired
    private  HistoryInterface historyService;


    @PostMapping("/get-all")
    public List<HistoryDataResponseDTO> getAllHistoryData(@RequestBody HistoryDataRequestDTO request) {
        return historyService.getHistoryData(request);
    }

}
