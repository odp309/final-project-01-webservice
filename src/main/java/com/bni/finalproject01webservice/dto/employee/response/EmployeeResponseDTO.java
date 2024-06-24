package com.bni.finalproject01webservice.dto.employee.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EmployeeResponseDTO {

    private UUID id;
    private String branchCode;
    private String roleName;
    private String email;
    private String firstName;
    private String lastName;
    private String nip;
    private Boolean isActive;
    private String createdAt;
    private String updatedAt;
}
