package com.bni.finalproject01webservice.controller.private_api;

import com.bni.finalproject01webservice.dto.response.InitResponseDTO;
import com.bni.finalproject01webservice.service.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/private/transaction")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API", description = "Private API secured with JWT token")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/type")
    public ResponseEntity<InitResponseDTO> initTransactionType() {
        InitResponseDTO result = transactionService.initTransactionType();
        return ResponseEntity.ok(result);
    }


}
