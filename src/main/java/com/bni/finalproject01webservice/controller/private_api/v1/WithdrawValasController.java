package com.bni.finalproject01webservice.controller.private_api.v1;

import com.bni.finalproject01webservice.dto.withdraw_valas.request.DetailWithdrawValasRequestDTO;
import com.bni.finalproject01webservice.dto.withdraw_valas.request.WithdrawValasRequestDTO;
import com.bni.finalproject01webservice.dto.withdraw_valas.response.DetailWithdrawValasResponseDTO;
import com.bni.finalproject01webservice.dto.withdraw_valas.response.WithdrawValasResponseDTO;
import com.bni.finalproject01webservice.interfaces.WithdrawValasInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/private/withdraw-valas")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API", description = "Private API secured with JWT token")
public class WithdrawValasController {

    @Autowired
    private WithdrawValasInterface withdrawValasService;

    @PostMapping("/detail")
    public DetailWithdrawValasResponseDTO detailWithdrawValas(@RequestBody DetailWithdrawValasRequestDTO request) {
        return withdrawValasService.detailWithdrawValas(request);
    }

    @PostMapping("/withdraw")
    public WithdrawValasResponseDTO withdrawValas(@RequestBody WithdrawValasRequestDTO request) {
        return withdrawValasService.withdrawValas(request);
    }
}