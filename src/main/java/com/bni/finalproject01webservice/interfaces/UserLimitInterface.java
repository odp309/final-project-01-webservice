package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.user_limit.request.UserLimitRequestDTO;
import com.bni.finalproject01webservice.dto.user_limit.response.UserLimitResponseDTO;

public interface UserLimitInterface {

    UserLimitResponseDTO addUserLimit(UserLimitRequestDTO request);

    void resetUserLimit();
}
