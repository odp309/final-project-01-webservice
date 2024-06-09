package com.bni.finalproject01webservice.dto.branch.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchResponseDTO {

    private String name;
    private String type;
    private String phone;
    private int codeArea;
    private String address;
    private String city;
    private String province;
}
