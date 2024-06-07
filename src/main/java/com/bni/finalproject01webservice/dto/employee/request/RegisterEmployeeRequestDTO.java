package com.bni.finalproject01webservice.dto.employee.request;

import lombok.Data;

import java.util.UUID;

@Data
public class RegisterEmployeeRequestDTO {

    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String nip;
    private String branchName;
}
