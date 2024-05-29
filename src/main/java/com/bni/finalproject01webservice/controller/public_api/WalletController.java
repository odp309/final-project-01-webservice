package com.bni.finalproject01webservice.controller.public_api;

import com.bni.finalproject01webservice.dto.WalletRequestDTO;
import com.bni.finalproject01webservice.dto.WalletResponseDTO;
import com.bni.finalproject01webservice.interfaces.WalletInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public/")
public class WalletController {

    @Autowired
    private WalletInterface walletService;

    @PostMapping("/getWallet")
    public WalletResponseDTO getWallet(@RequestBody WalletRequestDTO requestDTO) {
        return walletService.getWallet(requestDTO.getAccountNumber());
    }
}