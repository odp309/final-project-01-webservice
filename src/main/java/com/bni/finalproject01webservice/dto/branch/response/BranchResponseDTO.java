package com.bni.finalproject01webservice.dto.branch.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BranchResponseDTO {

    private String code;
    private String name;
    private String type;
    private String phone;
    private String address;
    private String city;
    private String province;
}
