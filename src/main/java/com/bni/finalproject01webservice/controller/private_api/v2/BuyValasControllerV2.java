package com.bni.finalproject01webservice.controller.private_api.v2;

import com.bni.finalproject01webservice.dto.buy_valas.request.BuyValasRequestDTO;
import com.bni.finalproject01webservice.dto.buy_valas.request.DetailBuyValasRequestDTO;
import com.bni.finalproject01webservice.dto.buy_valas.request.LimitCheckRequestDTO;
import com.bni.finalproject01webservice.dto.buy_valas.response.BuyValasResponseDTO;
import com.bni.finalproject01webservice.dto.buy_valas.response.DetailBuyValasResponseDTO;
import com.bni.finalproject01webservice.dto.buy_valas.response.LimitCheckResponseDTO;
import com.bni.finalproject01webservice.interfaces.BuyValasInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/private/buy-valas")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API V2", description = "Private API secured with JWT token")
public class BuyValasControllerV2 {

    @Autowired
    private BuyValasInterface buyValasService;

    @PostMapping("/detail")
    public DetailBuyValasResponseDTO detailBuyValas(@RequestBody DetailBuyValasRequestDTO request, HttpServletRequest headerRequest) {
        return buyValasService.detailBuyValas(request, headerRequest);
    }

    @PostMapping("/buy")
    public BuyValasResponseDTO buyValas(@RequestBody BuyValasRequestDTO request, HttpServletRequest headerRequest) {
        return buyValasService.buyValas(request, headerRequest);
    }

    @PostMapping("/limit-check")
    public LimitCheckResponseDTO limitCheck(HttpServletRequest headerRequest) {
        return buyValasService.getCurrentUserLimit( headerRequest);
    }
}
