package com.bni.finalproject01webservice.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CurrencyResponseDTO {

    private String code;
    private String name;
    private Date createdAt;
    private Date updatedAt;
}
