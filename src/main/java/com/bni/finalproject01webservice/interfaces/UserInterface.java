package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.*;

public interface UserInterface {

    InitResponseDTO initRoleAndUser();

    LoginResponseDTO login(LoginRequestDTO request);

    RegisterResponseDTO register(RegisterRequestDTO request);
}
