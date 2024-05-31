package com.bni.finalproject01webservice.controller.private_api;

import com.bni.finalproject01webservice.dto.request.WalletRequestDTO;
import com.bni.finalproject01webservice.dto.response.WalletResponseDTO;
import com.bni.finalproject01webservice.interfaces.WalletInterface;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/private/wallet")
@Tag(name = "Public API", description = "Public API open to the public")
public class WalletController {

    @Autowired
    private WalletInterface walletService;

    @PostMapping("/getWallet")
    public WalletResponseDTO getWallet(@RequestBody WalletRequestDTO requestDTO) {
        return walletService.getWallet(requestDTO.getAccountNumber());
    }
}