package com.bni.finalproject01webservice.dto.employee.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class InvokePasswordResetEmployeeRequestDTO {

    private UUID employeeId;
}
