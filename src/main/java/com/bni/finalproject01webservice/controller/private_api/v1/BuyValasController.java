package com.bni.finalproject01webservice.controller.private_api.v1;

import com.bni.finalproject01webservice.dto.buy_valas.request.BuyValasRequestDTO;
import com.bni.finalproject01webservice.dto.buy_valas.request.DetailBuyValasRequestDTO;
import com.bni.finalproject01webservice.dto.buy_valas.request.LimitCheckRequestDTO;
import com.bni.finalproject01webservice.dto.buy_valas.response.BuyValasResponseDTO;
import com.bni.finalproject01webservice.dto.buy_valas.response.DetailBuyValasResponseDTO;
import com.bni.finalproject01webservice.dto.buy_valas.response.LimitCheckResponseDTO;
import com.bni.finalproject01webservice.service.BuyValasService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/private/buy-valas")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API", description = "Private API secured with JWT token")
public class BuyValasController {

    @Autowired
    private BuyValasService buyValasService;

    @PostMapping("/detail")
    public DetailBuyValasResponseDTO detailBuyValas(@RequestBody DetailBuyValasRequestDTO request) {
        return buyValasService.detailBuyValas(request);
    }

    @PostMapping("/buy")
    public BuyValasResponseDTO buyValas(@RequestBody BuyValasRequestDTO request) {
        return buyValasService.buyValas(request);
    }

    @PostMapping("/limit-cek")
    public LimitCheckResponseDTO limitCheck(@RequestBody LimitCheckRequestDTO request) {
        return buyValasService.getCurrentUserLimt(request);
    }
}
