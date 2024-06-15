package com.bni.finalproject01webservice.controller.private_api.v2;

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
import jakarta.servlet.http.HttpServletRequest;
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
    public BankAccountResponseDTO addBankAccount(@RequestBody AddBankAccountRequestDTO request, HttpServletRequest headerRequest) {
        return bankAccountInterface.addBankAccount(request, headerRequest);
    }

    @PostMapping("/get")
    public BankAccountResponseDTO getBankAccount(@RequestBody GetBankAccountRequestDTO request, HttpServletRequest headerRequest) {
        return bankAccountInterface.getBankAccount(request, headerRequest);
    }

    @PostMapping("/get-all")
    public List<BankAccountWithWalletResponseDTO> getAllBankAccountOfUser(@RequestBody GetAllBankAccountRequestDTO request, HttpServletRequest headerRequest) {
        return bankAccountInterface.getAllBankAccountOfUser(request, headerRequest);
    }

    @PostMapping("/get-with-wallet")
    public GetBankAccountWalletResponseDTO getBankAccountWallet(@RequestBody GetBankAccountWalletRequestDTO request, HttpServletRequest headerRequest) {
        return bankAccountInterface.getBankAccountWallet(request, headerRequest);
    }
}