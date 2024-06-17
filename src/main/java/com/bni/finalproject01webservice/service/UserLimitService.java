package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.configuration.exceptions.UserException;
import com.bni.finalproject01webservice.dto.user_limit.request.UserLimitRequestDTO;
import com.bni.finalproject01webservice.dto.user_limit.response.UserLimitResponseDTO;
import com.bni.finalproject01webservice.interfaces.UserLimitInterface;
import com.bni.finalproject01webservice.model.User;
import com.bni.finalproject01webservice.model.UserLimit;
import com.bni.finalproject01webservice.repository.UserLimitRepository;
import com.bni.finalproject01webservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserLimitService implements UserLimitInterface {

    private final UserLimitRepository userLimitRepository;
    private final UserRepository userRepository;

    @Override
    public UserLimitResponseDTO addUserLimit(UserLimitRequestDTO request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new UserException("User not found!"));

        UserLimit userLimit = new UserLimit();
        userLimit.setLimitAccumulation(BigDecimal.valueOf(100000));
        userLimit.setUser(user);
        userLimit.setCreatedAt(new Date());
        userLimitRepository.save(userLimit);

        UserLimitResponseDTO response = new UserLimitResponseDTO();
        response.setUserLimitId(userLimit.getId());

        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetUserLimit() {
        userLimitRepository.resetLimit();
    }
}
