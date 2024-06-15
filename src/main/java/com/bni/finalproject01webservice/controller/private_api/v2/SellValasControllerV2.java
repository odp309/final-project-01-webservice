package com.bni.finalproject01webservice.controller.private_api.v2;

import com.bni.finalproject01webservice.dto.sell_valas.request.DetailSellValasRequestDTO;
import com.bni.finalproject01webservice.dto.sell_valas.request.SellValasRequestDTO;
import com.bni.finalproject01webservice.dto.sell_valas.response.DetailSellValasResponseDTO;
import com.bni.finalproject01webservice.dto.sell_valas.response.SellValasResponseDTO;
import com.bni.finalproject01webservice.interfaces.SellValasInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/private/sell-valas")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API V2", description = "Private API secured with JWT token")
public class SellValasControllerV2 {

    @Autowired
    private SellValasInterface sellValasService;

    @PostMapping("/detail")
    public DetailSellValasResponseDTO detailSellValas(@RequestBody DetailSellValasRequestDTO request, HttpServletRequest headerRequest) {
        return sellValasService.detailSellValas(request, headerRequest);
    }

    @PostMapping("/sell")
    public SellValasResponseDTO sellValas(@RequestBody SellValasRequestDTO request, HttpServletRequest headerRequest) {
        return sellValasService.sellValas(request, headerRequest);
    }
}