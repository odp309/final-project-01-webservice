package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.request.LoginRequestDTO;
import com.bni.finalproject01webservice.dto.request.RegisterEmployeeRequestDTO;
import com.bni.finalproject01webservice.dto.response.InitResponseDTO;
import com.bni.finalproject01webservice.dto.response.LoginResponseDTO;
import com.bni.finalproject01webservice.dto.response.RegisterResponseDTO;

public interface EmployeeInterface {

    InitResponseDTO initRoleAndEmployee();

    LoginResponseDTO login(LoginRequestDTO request);

    RegisterResponseDTO registerAdmin(RegisterEmployeeRequestDTO request);

    RegisterResponseDTO registerTeller(RegisterEmployeeRequestDTO request);
}
