package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.request.RoleRequestDTO;
import com.bni.finalproject01webservice.interfaces.RoleInterface;
import com.bni.finalproject01webservice.model.Role;
import com.bni.finalproject01webservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService implements RoleInterface {

    private final RoleRepository roleRepository;

    @Override
    public Role createNewRole(RoleRequestDTO roleRequestDTO) {
        Role role = new Role();
        role.setName(roleRequestDTO.getRoleName());
        role.setDescription(roleRequestDTO.getRoleDescription());

        return roleRepository.save(role);
    }
}
