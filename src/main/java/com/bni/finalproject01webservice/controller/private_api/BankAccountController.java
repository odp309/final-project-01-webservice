package com.bni.finalproject01webservice.controller.private_api;

import com.bni.finalproject01webservice.dto.request.BankAccountRequestDTO;
import com.bni.finalproject01webservice.dto.response.BankAccountResponseDTO;
import com.bni.finalproject01webservice.interfaces.BankAccountInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/private/bank_account")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API", description = "Private API secured with JWT token")
public class BankAccountController {

    @Autowired
    private BankAccountInterface bankAccountInterface;

    @PostMapping("/saldo")
    public BankAccountResponseDTO getSaldo(@RequestBody BankAccountRequestDTO AccountNumberRequest) {
        return bankAccountInterface.getSaldo(AccountNumberRequest.getAccountNumber());
    }
}
