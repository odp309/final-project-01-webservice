package com.bni.finalproject01webservice.dto.employee.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IsPasswordChangeEmployeeResponseDTO {

    private String token;
    private Boolean isPasswordChange;
}
