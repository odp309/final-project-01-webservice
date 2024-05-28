package com.bni.finalproject01webservice.controller.private_api;

import com.bni.finalproject01webservice.dto.*;
import com.bni.finalproject01webservice.interfaces.EmployeeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/private/employee")
public class EmployeeController {

    @Autowired
    private EmployeeInterface employeeService;

    @PostMapping("/for-admin-mgr/register/admin")
    @PreAuthorize("hasRole('ADMIN_MGR')")
    public ResponseEntity<RegisterResponseDTO> registerAdmin(@RequestBody RegisterEmployeeRequestDTO request) {
        RegisterResponseDTO result = employeeService.registerAdmin(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/for-admin-mgr/register/teller")
    @PreAuthorize("hasRole('ADMIN_MGR')")
    public ResponseEntity<RegisterResponseDTO> registerTeller(@RequestBody RegisterEmployeeRequestDTO request) {
        RegisterResponseDTO result = employeeService.registerTeller(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping({"/for-admin-mgr"})
    @PreAuthorize("hasRole('ADMIN_MGR')")
    public String forAdminMgr() {
        return "This URL is only accessible to the admin manager";
    }

    @GetMapping({"/for-admin"})
    @PreAuthorize("hasRole('ADMIN')")
    public String forAdmin() {
        return "This URL is only accessible to the admin";
    }

    @GetMapping({"/for-teller"})
    @PreAuthorize("hasRole('TELLER')")
    public String forTeller() {
        return "This URL is only accessible to the teller";
    }
}
