package com.bni.finalproject01webservice.dto.response;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private String accessToken;
    private String refreshToken;
}
