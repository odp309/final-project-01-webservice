package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.*;

public interface UserInterface {

    LoginResponseDTO login(LoginRequestDTO request);

    RegisterResponseDTO register(RegisterUserRequestDTO request);
}
