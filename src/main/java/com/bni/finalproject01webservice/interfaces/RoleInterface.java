package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.role.request.RoleRequestDTO;
import com.bni.finalproject01webservice.dto.role.response.RoleResponseDTO;
import com.bni.finalproject01webservice.model.Role;

import java.util.List;

public interface RoleInterface {

    Role createNewRole(RoleRequestDTO request);

    List<RoleResponseDTO> getAllRole();
}
