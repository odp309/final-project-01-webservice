package com.bni.finalproject01webservice.controller.private_api.v2;

import com.bni.finalproject01webservice.dto.currency.request.GetMinimumCurrencyRequestDTO;
import com.bni.finalproject01webservice.dto.currency.response.CurrencyResponseDTO;
import com.bni.finalproject01webservice.dto.currency.response.GetMinimumCurrencyResponseDTO;
import com.bni.finalproject01webservice.interfaces.CurrencyInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/private/currency")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API V2", description = "Private API secured with JWT token")
public class CurrencyControllerV2 {

    @Autowired
    private CurrencyInterface currencyService;

    @GetMapping("/get-all")
    public List<CurrencyResponseDTO> registerAdmin() {
        return currencyService.getAllCurrency();
    }

    @PostMapping("/minimum-buy/get")
    public GetMinimumCurrencyResponseDTO getMinimumBuy(@RequestBody GetMinimumCurrencyRequestDTO request) {
        return currencyService.getMinimumBuy(request);
    }

    @PostMapping("/minimum-sell/get")
    public GetMinimumCurrencyResponseDTO getMinimumSell(@RequestBody GetMinimumCurrencyRequestDTO request) {
        return currencyService.getMinimumSell(request);
    }

    @PostMapping("/minimum-transfer/get")
    public GetMinimumCurrencyResponseDTO getMinimumTransfer(@RequestBody GetMinimumCurrencyRequestDTO request) {
        return currencyService.getMinimumTransfer(request);
    }

    @PostMapping("/minimum-deposit/get")
    public GetMinimumCurrencyResponseDTO getMinimumDeposit(@RequestBody GetMinimumCurrencyRequestDTO request) {
        return currencyService.getMinimumDeposit(request);
    }

    @PostMapping("/minimum-withdrawal/get")
    public GetMinimumCurrencyResponseDTO getMinimumWithdrawal(@RequestBody GetMinimumCurrencyRequestDTO request) {
        return currencyService.getMinimumWithdrawal(request);
    }
}