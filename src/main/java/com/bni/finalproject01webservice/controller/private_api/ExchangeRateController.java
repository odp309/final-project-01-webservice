package com.bni.finalproject01webservice.controller.private_api;

import com.bni.finalproject01webservice.dto.request.ExchangeRateRequestDTO;
import com.bni.finalproject01webservice.dto.response.ExchangeRateResponseDTO;
import com.bni.finalproject01webservice.dto.response.FrankfurterResponseDTO;
import com.bni.finalproject01webservice.interfaces.ExchangeRateInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/private/exchange-rate")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API", description = "Private API secured with JWT token")
public class ExchangeRateController {

    @Autowired
    private ExchangeRateInterface exchangeRateService;

    @GetMapping("/frankfurter/add")
    public FrankfurterResponseDTO addExchangeRateFrankfurter() {
        return exchangeRateService.addExchangeRateFrankfurter();
    }

    @PostMapping("/get-single")
    public ExchangeRateResponseDTO getSingleExchangeRateFrankfurter(@RequestBody ExchangeRateRequestDTO request) {
        return exchangeRateService.getSingleExchangeRate(request);
    }
}

