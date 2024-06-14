package com.bni.finalproject01webservice.controller.private_api.v2;

import com.bni.finalproject01webservice.dto.role.request.RoleRequestDTO;
import com.bni.finalproject01webservice.dto.role.response.RoleResponseDTO;
import com.bni.finalproject01webservice.interfaces.RoleInterface;
import com.bni.finalproject01webservice.model.Role;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/private/role")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API V2", description = "Private API secured with JWT token")
public class RoleControllerV2 {

    @Autowired
    private RoleInterface roleService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN_MGR')")
    public Role createNewRole(@RequestBody RoleRequestDTO roleRequestDTO) {
        return roleService.createNewRole(roleRequestDTO);
    }

    @GetMapping("/get-all")
    public List<RoleResponseDTO> getAllRole() {
        return roleService.getAllRole();
    }
}
