package com.bni.finalproject01webservice.dto;

import lombok.Data;

@Data
public class RegisterRequestDTO {

    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
