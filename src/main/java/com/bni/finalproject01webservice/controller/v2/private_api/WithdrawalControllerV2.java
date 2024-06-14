package com.bni.finalproject01webservice.controller.v2.private_api;

import com.bni.finalproject01webservice.dto.withdrawal.request.ReportWithdrawalRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal.response.ReportWithdrawalResponseDTO;
import com.bni.finalproject01webservice.interfaces.WithdrawalInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/private/withdrawal")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API V2", description = "Private API secured with JWT token")
public class WithdrawalControllerV2 {

    @Autowired
    WithdrawalInterface withdrawalService;

    @PostMapping("/get-report")
    public ReportWithdrawalResponseDTO getReport(@RequestBody ReportWithdrawalRequestDTO request) {
        return withdrawalService.reportWithdrawal(request);
    }
}
