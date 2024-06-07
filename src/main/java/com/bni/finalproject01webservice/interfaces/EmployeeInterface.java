package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.auth.request.LoginRequestDTO;
import com.bni.finalproject01webservice.dto.employee.request.ActivateEmployeeRequestDTO;
import com.bni.finalproject01webservice.dto.employee.request.GetAllEmployeeRequestDTO;
import com.bni.finalproject01webservice.dto.employee.request.RegisterEmployeeRequestDTO;
import com.bni.finalproject01webservice.dto.employee.response.ActivateEmployeeResponseDTO;
import com.bni.finalproject01webservice.dto.employee.response.EmployeeResponseDTO;
import com.bni.finalproject01webservice.dto.init.response.InitResponseDTO;
import com.bni.finalproject01webservice.dto.auth.response.LoginResponseDTO;
import com.bni.finalproject01webservice.dto.auth.response.RegisterResponseDTO;

import java.util.List;

public interface EmployeeInterface {

    InitResponseDTO initRoleAndEmployee();

    LoginResponseDTO login(LoginRequestDTO request);

    RegisterResponseDTO registerAdmin(RegisterEmployeeRequestDTO request);

    RegisterResponseDTO registerTeller(RegisterEmployeeRequestDTO request);

    List<EmployeeResponseDTO> getAllEmployee(GetAllEmployeeRequestDTO request);

    ActivateEmployeeResponseDTO activateEmployee(ActivateEmployeeRequestDTO request);
}
