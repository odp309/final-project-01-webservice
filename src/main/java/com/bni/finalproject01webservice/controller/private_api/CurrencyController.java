package com.bni.finalproject01webservice.controller.private_api;

import com.bni.finalproject01webservice.dto.currency.response.CurrencyResponseDTO;
import com.bni.finalproject01webservice.interfaces.CurrencyInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/private/currency")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API", description = "Private API secured with JWT token")
public class CurrencyController {

    @Autowired
    private CurrencyInterface currencyService;

    @GetMapping("/get-all")
    public List<CurrencyResponseDTO> registerAdmin() {
        return currencyService.getAllCurrency();
    }
}
