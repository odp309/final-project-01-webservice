package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.role.request.RoleRequestDTO;
import com.bni.finalproject01webservice.dto.role.response.RoleResponseDTO;
import com.bni.finalproject01webservice.interfaces.RoleInterface;
import com.bni.finalproject01webservice.model.Role;
import com.bni.finalproject01webservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<RoleResponseDTO> getAllRole() {
        List<Role> roles = roleRepository.findAll();

        return roles.stream()
                .map(role -> {
                    RoleResponseDTO response = new RoleResponseDTO();
                    response.setId(role.getId());
                    response.setName(role.getName());
                    return response;
                })
                .collect(Collectors.toList());
    }
}
