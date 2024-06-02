package com.bni.finalproject01webservice.controller.private_api;

import com.bni.finalproject01webservice.dto.request.AddBankAccountRequestDTO;
import com.bni.finalproject01webservice.dto.request.GetAllBankAccountRequestDTO;
import com.bni.finalproject01webservice.dto.request.GetBankAccountRequestDTO;
import com.bni.finalproject01webservice.dto.response.BankAccountResponseDTO;
import com.bni.finalproject01webservice.interfaces.BankAccountInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/private/bank_account")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API", description = "Private API secured with JWT token")
public class BankAccountController {

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
    public List<BankAccountResponseDTO> getAllBankAccountOfUser(@RequestBody GetAllBankAccountRequestDTO request) {
        return bankAccountInterface.getAllBankAccountOfUser(request);
    }
}
