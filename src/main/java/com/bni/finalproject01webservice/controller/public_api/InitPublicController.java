package com.bni.finalproject01webservice.controller.public_api;

import com.bni.finalproject01webservice.dto.init.response.InitResponseDTO;
import com.bni.finalproject01webservice.interfaces.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/public/init")
@Tag(name = "Public API", description = "Public API open to the public")
public class InitPublicController {

    private final EmployeeInterface employeeService;
    private final CurrencyInterface currencyService;
    private final BranchInterface branchService;
    private final TrxTypeInterface trxTypeService;
    private final OperationTypeInterface operationTypeService;

    @PostMapping("/role-and-employee")
    public ResponseEntity<InitResponseDTO> initRoleAndEmployee() {
        InitResponseDTO result = employeeService.initRoleAndEmployee();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/currency")
    public ResponseEntity<InitResponseDTO> initCurrency() {
        InitResponseDTO result = currencyService.initCurrency();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/trx_type")
    public ResponseEntity<InitResponseDTO> initTrxType() {
        InitResponseDTO result = trxTypeService.initTrxType();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/operation_type")
    public ResponseEntity<InitResponseDTO> initOperationType() {
        InitResponseDTO result = operationTypeService.initOperationType();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/branch")
    public ResponseEntity<InitResponseDTO> initBranch() {
        InitResponseDTO result = branchService.initBranch();
        return ResponseEntity.ok(result);
    }
}