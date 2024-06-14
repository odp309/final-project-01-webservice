package com.bni.finalproject01webservice.controller.private_api.v2;

import com.bni.finalproject01webservice.dto.history.request.HistoryDataRequestDTO;
import com.bni.finalproject01webservice.dto.history.request.HistoryDetailRequestDTO;
import com.bni.finalproject01webservice.dto.history.response.HistoryDataResponseDTO;
import com.bni.finalproject01webservice.dto.history.response.HistoryDetailResponseDTO;
import com.bni.finalproject01webservice.interfaces.HistoryInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/private/history")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API V2", description = "Private API secured with JWT token")
public class HistoryControllerV2 {

    @Autowired
    private  HistoryInterface historyService;

    @PostMapping("/get-all")
    public List<HistoryDataResponseDTO> getAllHistoryData(@RequestBody HistoryDataRequestDTO request) {
        return historyService.getHistoryData(request);
    }

    @PostMapping("/get-detail")
    public HistoryDetailResponseDTO getHistoryDetail(@RequestBody HistoryDetailRequestDTO request) {
        return historyService.getHistoryDetail(request);
    }

}
