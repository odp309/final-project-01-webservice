package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.auth.request.LoginRequestDTO;
import com.bni.finalproject01webservice.dto.employee.request.*;
import com.bni.finalproject01webservice.dto.employee.response.ActivateEmployeeResponseDTO;
import com.bni.finalproject01webservice.dto.employee.response.EmployeeResponseDTO;
import com.bni.finalproject01webservice.dto.employee.response.InvokePasswordResetEmployeeResponseDTO;
import com.bni.finalproject01webservice.dto.employee.response.PasswordResetEmployeeResponseDTO;
import com.bni.finalproject01webservice.dto.init.response.InitResponseDTO;
import com.bni.finalproject01webservice.dto.auth.response.LoginResponseDTO;
import com.bni.finalproject01webservice.dto.auth.response.RegisterResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface EmployeeInterface {

    InitResponseDTO initRoleAndEmployee();

    LoginResponseDTO login(LoginRequestDTO request);

    RegisterResponseDTO registerAdmin(RegisterEmployeeRequestDTO request, HttpServletRequest headerRequest);

    RegisterResponseDTO registerTeller(RegisterEmployeeRequestDTO request, HttpServletRequest headerRequest);

    List<EmployeeResponseDTO> getAllEmployee(GetAllEmployeeRequestDTO request);

    ActivateEmployeeResponseDTO activateEmployee(ActivateEmployeeRequestDTO request);

    //////////////////////////////// VERSION 2.0 BLOCK ////////////////////////////////

    List<EmployeeResponseDTO> getAllEmployee(GetAllEmployeeRequestDTO request, HttpServletRequest headerRequest);

    PasswordResetEmployeeResponseDTO passwordResetEmployee(PasswordResetEmployeeRequestDTO request);

    InvokePasswordResetEmployeeResponseDTO invokePasswordResetEmployee(InvokePasswordResetEmployeeRequestDTO request);
}
