package com.bni.finalproject01webservice.dto.user.request;

import lombok.Data;

@Data
public class RegisterUserRequestDTO {

    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String pin;
}
