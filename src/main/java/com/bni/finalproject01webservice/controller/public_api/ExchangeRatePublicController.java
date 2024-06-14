package com.bni.finalproject01webservice.controller.public_api;

import com.bni.finalproject01webservice.dto.exchange_rate.response.ExchangeRateResponseDTO;
import com.bni.finalproject01webservice.interfaces.ExchangeRateInterface;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/exchange_rate")
@Tag(name = "Public API", description = "Public API open to the public")
public class ExchangeRatePublicController {

    @Autowired
    private ExchangeRateInterface exchangeRateService;

    @GetMapping("/get-all")
    public List<ExchangeRateResponseDTO> getAllExchangeRate() {
        return exchangeRateService.getAllExchangeRate();
    }
}
