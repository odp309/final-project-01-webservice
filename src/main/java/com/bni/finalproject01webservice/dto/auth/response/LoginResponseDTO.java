package com.bni.finalproject01webservice.dto.auth.response;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private String accessToken;
    private String refreshToken;
    private String resetToken;
}
