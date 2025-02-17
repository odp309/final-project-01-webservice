package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.auth.request.LoginRequestDTO;
import com.bni.finalproject01webservice.dto.user.request.RegisterUserRequestDTO;
import com.bni.finalproject01webservice.dto.auth.response.LoginResponseDTO;
import com.bni.finalproject01webservice.dto.auth.response.RegisterResponseDTO;

public interface UserInterface {

    LoginResponseDTO login(LoginRequestDTO request);

    RegisterResponseDTO register(RegisterUserRequestDTO request);

    void userIsCooldownChecker();
}
