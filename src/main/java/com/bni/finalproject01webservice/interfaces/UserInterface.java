package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.request.LoginRequestDTO;
import com.bni.finalproject01webservice.dto.request.RegisterUserRequestDTO;
import com.bni.finalproject01webservice.dto.response.LoginResponseDTO;
import com.bni.finalproject01webservice.dto.response.RegisterResponseDTO;

public interface UserInterface {

    LoginResponseDTO login(LoginRequestDTO request);

    RegisterResponseDTO register(RegisterUserRequestDTO request);
}
