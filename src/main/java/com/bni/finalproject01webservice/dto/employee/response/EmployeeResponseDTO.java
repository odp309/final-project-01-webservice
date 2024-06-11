package com.bni.finalproject01webservice.dto.employee.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class EmployeeResponseDTO {

    private UUID id;
    private String branchCode;
    private String roleName;
    private String email;
    private String firstName;
    private String lastName;
    private String nip;
    private Boolean isActive;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Jakarta")
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Jakarta")
    private Date updatedAt;
}
