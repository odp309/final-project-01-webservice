package com.bni.finalproject01webservice.controller.private_api.v2;

import com.bni.finalproject01webservice.dto.wallet.request.AddWalletRequestDTO;
import com.bni.finalproject01webservice.dto.wallet.response.WalletResponseDTO;
import com.bni.finalproject01webservice.interfaces.WalletInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/private/wallet")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API V2", description = "Private API secured with JWT token")
public class WalletControllerV2 {

    @Autowired
    private WalletInterface walletService;

    @PostMapping("/add")
    public WalletResponseDTO addWallet(@RequestBody AddWalletRequestDTO request, HttpServletRequest headerRequest) {
        return walletService.addWallet(request, headerRequest);
    }
}