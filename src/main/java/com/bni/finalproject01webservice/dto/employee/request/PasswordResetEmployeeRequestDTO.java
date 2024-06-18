package com.bni.finalproject01webservice.dto.employee.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetEmployeeRequestDTO {

    private String token;
    private String newPassword;
}
