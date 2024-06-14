package com.bni.finalproject01webservice.controller.private_api.v1;

import com.bni.finalproject01webservice.dto.employee.request.ActivateEmployeeRequestDTO;
import com.bni.finalproject01webservice.dto.employee.request.GetAllEmployeeRequestDTO;
import com.bni.finalproject01webservice.dto.employee.request.RegisterEmployeeRequestDTO;
import com.bni.finalproject01webservice.dto.auth.response.RegisterResponseDTO;
import com.bni.finalproject01webservice.dto.employee.response.ActivateEmployeeResponseDTO;
import com.bni.finalproject01webservice.dto.employee.response.EmployeeResponseDTO;
import com.bni.finalproject01webservice.interfaces.EmployeeInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/private/employee")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API", description = "Private API secured with JWT token")
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

    @PostMapping("/for-admin-mgr/get-all-employees")
    @PreAuthorize("hasRole('ADMIN_MGR')")
    public List<EmployeeResponseDTO> getAllEmployee(@RequestBody GetAllEmployeeRequestDTO request) {
        return employeeService.getAllEmployee(request);
    }

    @PostMapping("/for-admin-mgr/activate-employee")
    @PreAuthorize("hasRole('ADMIN_MGR')")
    public ActivateEmployeeResponseDTO activateEmployee(@RequestBody ActivateEmployeeRequestDTO request) {
        return employeeService.activateEmployee(request);
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
