package com.bni.finalproject01webservice.controller.private_api.v2;

import com.bni.finalproject01webservice.dto.employee.request.GetAllEmployeeRequestDTO;
import com.bni.finalproject01webservice.dto.employee.request.InvokePasswordResetEmployeeRequestDTO;
import com.bni.finalproject01webservice.dto.employee.request.RegisterEmployeeRequestDTO;
import com.bni.finalproject01webservice.dto.auth.response.RegisterResponseDTO;
import com.bni.finalproject01webservice.dto.employee.response.EmployeeResponseDTO;
import com.bni.finalproject01webservice.dto.employee.response.InvokePasswordResetEmployeeResponseDTO;
import com.bni.finalproject01webservice.interfaces.EmployeeInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/private/employee")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API V2", description = "Private API secured with JWT token")
public class EmployeeControllerV2 {

    @Autowired
    private EmployeeInterface employeeService;

    @PostMapping("/for-admin-mgr/register/admin")
    @PreAuthorize("hasRole('ADMIN_MGR')")
    public ResponseEntity<RegisterResponseDTO> registerAdmin(@RequestBody RegisterEmployeeRequestDTO request, HttpServletRequest headerRequest) {
        RegisterResponseDTO result = employeeService.registerAdmin(request, headerRequest);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/for-admin-mgr/register/teller")
    @PreAuthorize("hasRole('ADMIN_MGR')")
    public ResponseEntity<RegisterResponseDTO> registerTeller(@RequestBody RegisterEmployeeRequestDTO request, HttpServletRequest headerRequest) {
        RegisterResponseDTO result = employeeService.registerTeller(request, headerRequest);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/for-admin-mgr/get-all-employees")
    @PreAuthorize("hasRole('ADMIN_MGR')")
    public List<EmployeeResponseDTO> getAllEmployee(@RequestBody GetAllEmployeeRequestDTO request, HttpServletRequest headerRequest) {
        return employeeService.getAllEmployee(request, headerRequest);
    }

    @PostMapping("/for-admin-mgr/invoke-password-reset")
    @PreAuthorize("hasRole('ADMIN_MGR')")
    public InvokePasswordResetEmployeeResponseDTO invokePasswordResetEmployee(@RequestBody InvokePasswordResetEmployeeRequestDTO request) {
        return employeeService.invokePasswordResetEmployee(request);
    }
}
