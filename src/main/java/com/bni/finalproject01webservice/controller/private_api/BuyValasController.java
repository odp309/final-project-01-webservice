package com.bni.finalproject01webservice.controller.private_api;

import com.bni.finalproject01webservice.dto.request.DetailBuyValasRequestDTO;
import com.bni.finalproject01webservice.dto.response.DetailBuyValasResponseDTO;
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
    private BuyValasService BuyValasService;
    @PostMapping("/post")
    public DetailBuyValasResponseDTO detailBuyValas(@RequestBody DetailBuyValasRequestDTO request) {
        return BuyValasService.detailBuyValas(request);
    }
}
