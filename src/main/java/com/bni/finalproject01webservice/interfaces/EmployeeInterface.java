package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.*;

public interface EmployeeInterface {

    InitResponseDTO initRoleAndEmployee();

    LoginResponseDTO login(LoginRequestDTO request);

    RegisterResponseDTO registerAdmin(RegisterEmployeeRequestDTO request);

    RegisterResponseDTO registerTeller(RegisterEmployeeRequestDTO request);
}
