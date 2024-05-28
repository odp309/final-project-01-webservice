package com.bni.finalproject01webservice.controller.private_api;

import com.bni.finalproject01webservice.dto.RoleRequestDTO;
import com.bni.finalproject01webservice.interfaces.RoleInterface;
import com.bni.finalproject01webservice.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/private/role")
public class RoleController {

    @Autowired
    private RoleInterface roleService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN_MGR')")
    public Role createNewRole(@RequestBody RoleRequestDTO roleRequestDTO) {
        return roleService.createNewRole(roleRequestDTO);
    }
}
