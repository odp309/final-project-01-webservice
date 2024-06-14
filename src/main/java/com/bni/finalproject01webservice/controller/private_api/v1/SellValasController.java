package com.bni.finalproject01webservice.controller.private_api.v1;

import com.bni.finalproject01webservice.dto.sell_valas.request.DetailSellValasRequestDTO;
import com.bni.finalproject01webservice.dto.sell_valas.request.SellValasRequestDTO;
import com.bni.finalproject01webservice.dto.sell_valas.response.DetailSellValasResponseDTO;
import com.bni.finalproject01webservice.dto.sell_valas.response.SellValasResponseDTO;
import com.bni.finalproject01webservice.service.SellValasService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/private/sell-valas")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API", description = "Private API secured with JWT token")
public class SellValasController {

    @Autowired
    private SellValasService sellValasService;

    @PostMapping("/detail")
    public DetailSellValasResponseDTO detailSellValas(@RequestBody DetailSellValasRequestDTO request) {
        return sellValasService.detailSellValas(request);
    }

    @PostMapping("/sell")
    public SellValasResponseDTO sellValas(@RequestBody SellValasRequestDTO request) {
        return sellValasService.sellValas(request);
    }
}