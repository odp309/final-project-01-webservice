package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.auth.request.LoginRequestDTO;
import com.bni.finalproject01webservice.dto.employee.request.DataEmployeeRequestDTO;
import com.bni.finalproject01webservice.dto.employee.request.RegisterEmployeeRequestDTO;
import com.bni.finalproject01webservice.dto.employee.response.DataEmployeeResponseDTO;
import com.bni.finalproject01webservice.dto.init.response.InitResponseDTO;
import com.bni.finalproject01webservice.dto.auth.response.LoginResponseDTO;
import com.bni.finalproject01webservice.dto.auth.response.RegisterResponseDTO;

public interface EmployeeInterface {

    InitResponseDTO initRoleAndEmployee();

    LoginResponseDTO login(LoginRequestDTO request);

    RegisterResponseDTO registerAdmin(RegisterEmployeeRequestDTO request);

    RegisterResponseDTO registerTeller(RegisterEmployeeRequestDTO request);


    DataEmployeeResponseDTO getAllEmployee(DataEmployeeRequestDTO request);
}
