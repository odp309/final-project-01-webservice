package com.bni.finalproject01webservice.dto.employee.request;

import lombok.Data;

@Data
public class RegisterEmployeeRequestDTO {

    private String email;
    private String firstName;
    private String lastName;
    private String nip;
}
