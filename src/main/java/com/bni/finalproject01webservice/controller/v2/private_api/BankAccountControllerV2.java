package com.bni.finalproject01webservice.controller.v2.private_api;

import com.bni.finalproject01webservice.dto.bank_account.request.AddBankAccountRequestDTO;
import com.bni.finalproject01webservice.dto.bank_account.request.GetAllBankAccountRequestDTO;
import com.bni.finalproject01webservice.dto.bank_account.request.GetBankAccountRequestDTO;
import com.bni.finalproject01webservice.dto.bank_account.request.GetBankAccountWalletRequestDTO;
import com.bni.finalproject01webservice.dto.bank_account.response.BankAccountResponseDTO;
import com.bni.finalproject01webservice.dto.bank_account.response.BankAccountWithWalletResponseDTO;
import com.bni.finalproject01webservice.dto.bank_account.response.GetBankAccountWalletResponseDTO;
import com.bni.finalproject01webservice.interfaces.BankAccountInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/private/bank_account")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API V2", description = "Private API secured with JWT token")
public class BankAccountControllerV2 {

    @Autowired
    private BankAccountInterface bankAccountInterface;

    @PostMapping("/add")
    public BankAccountResponseDTO addBankAccount(@RequestBody AddBankAccountRequestDTO request) {
        return bankAccountInterface.addBankAccount(request);
    }

    @PostMapping("/get")
    public BankAccountResponseDTO getBankAccount(@RequestBody GetBankAccountRequestDTO request) {
        return bankAccountInterface.getBankAccount(request);
    }

    @PostMapping("/get-all")
    public List<BankAccountWithWalletResponseDTO> getAllBankAccountOfUser(@RequestBody GetAllBankAccountRequestDTO request) {
        return bankAccountInterface.getAllBankAccountOfUser(request);
    }

    @PostMapping("/get-with-wallet")
    public GetBankAccountWalletResponseDTO getBankAccountWallet(@RequestBody GetBankAccountWalletRequestDTO request) {
        return bankAccountInterface.getBankAccountWallet(request);
    }
}