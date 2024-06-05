package com.bni.finalproject01webservice.dto.auth.request;

import lombok.Data;

@Data
public class LoginRequestDTO {

    private String email;
    private String password;
}
