package com.bni.finalproject01webservice.controller.private_api;

import com.bni.finalproject01webservice.dto.wallet.request.AddWalletRequestDTO;
import com.bni.finalproject01webservice.dto.wallet.request.WalletRequestDTO;
import com.bni.finalproject01webservice.dto.wallet.response.GetAllWalletResponseDTO;
import com.bni.finalproject01webservice.dto.wallet.response.WalletResponseDTO;
import com.bni.finalproject01webservice.interfaces.WalletInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/private/wallet")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API", description = "Private API secured with JWT token")
public class WalletController {

    @Autowired
    private WalletInterface walletService;

    @PostMapping("/getWallet")
    public GetAllWalletResponseDTO getWallet(@RequestBody WalletRequestDTO requestDTO) {
        return walletService.getWallet(requestDTO.getAccountNumber());
    }

    @PostMapping("/add")
    public WalletResponseDTO addWallet(@RequestBody AddWalletRequestDTO request) {
        return walletService.addWallet(request);
    }
}