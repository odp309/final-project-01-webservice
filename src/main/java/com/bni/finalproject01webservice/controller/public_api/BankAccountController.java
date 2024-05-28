package com.bni.finalproject01webservice.controller.public_api;

import com.bni.finalproject01webservice.dto.BankAccountResponseDTO;
import com.bni.finalproject01webservice.interfaces.BankAccountInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public/bank_account")
public class BankAccountController {

    @Autowired
    private BankAccountInterface bankAccountInterface;

    @GetMapping("/saldo/{noRekening}")
    public BankAccountResponseDTO getSaldo(@PathVariable String noRekening) {
        return bankAccountInterface.getSaldo(noRekening);
    }
}
