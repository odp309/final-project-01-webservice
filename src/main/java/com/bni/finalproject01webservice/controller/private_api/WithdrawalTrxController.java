package com.bni.finalproject01webservice.controller.private_api;

import com.bni.finalproject01webservice.dto.sell_valas.request.DetailSellValasRequestDTO;
import com.bni.finalproject01webservice.dto.sell_valas.request.SellValasRequestDTO;
import com.bni.finalproject01webservice.dto.sell_valas.response.DetailSellValasResponseDTO;
import com.bni.finalproject01webservice.dto.sell_valas.response.SellValasResponseDTO;
import com.bni.finalproject01webservice.dto.withdrawal_trx.request.DetailWithdrawalTrxRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal_trx.request.WithdrawalTrxRequestDTO;
import com.bni.finalproject01webservice.dto.withdrawal_trx.response.DetailWithdrawalTrxResponseDTO;
import com.bni.finalproject01webservice.dto.withdrawal_trx.response.WithdrawalTrxResponseDTO;
import com.bni.finalproject01webservice.interfaces.WithdrawalTrxInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/private/withdrawal-valas")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API", description = "Private API secured with JWT token")
public class WithdrawalTrxController {

    @Autowired
    private WithdrawalTrxInterface withdrawalTrxService;

    @PostMapping("/detail")
    public DetailWithdrawalTrxResponseDTO detailWithdrawalValas(@RequestBody DetailWithdrawalTrxRequestDTO request) {
        return withdrawalTrxService.detailWithdrawalTrx(request);
    }

    @PostMapping("/withdraw")
    public WithdrawalTrxResponseDTO withdrawalValas(@RequestBody WithdrawalTrxRequestDTO request) {
        return withdrawalTrxService.withdrawalTrx(request);
    }
}