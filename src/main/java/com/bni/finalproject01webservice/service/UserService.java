package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.configuration.exceptions.UserException;
import com.bni.finalproject01webservice.dto.auth.request.LoginRequestDTO;
import com.bni.finalproject01webservice.dto.user.request.RegisterUserRequestDTO;
import com.bni.finalproject01webservice.dto.auth.response.LoginResponseDTO;
import com.bni.finalproject01webservice.dto.auth.response.RegisterResponseDTO;
import com.bni.finalproject01webservice.dto.user_limit.request.UserLimitRequestDTO;
import com.bni.finalproject01webservice.dto.user_limit.response.UserLimitResponseDTO;
import com.bni.finalproject01webservice.interfaces.JWTInterface;
import com.bni.finalproject01webservice.interfaces.RefreshTokenInterface;
import com.bni.finalproject01webservice.interfaces.UserInterface;
import com.bni.finalproject01webservice.interfaces.UserLimitInterface;
import com.bni.finalproject01webservice.model.Role;
import com.bni.finalproject01webservice.model.User;
import com.bni.finalproject01webservice.repository.RoleRepository;
import com.bni.finalproject01webservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService implements UserInterface {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final JWTInterface jwtService;
    private final RefreshTokenInterface refreshTokenService;
    private final UserLimitInterface userLimitService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        User data = userRepository.findByEmail(request.getEmail());

//        if (data == null || !passwordEncoder.matches(request.getPassword(), data.getPassword())) {
//            throw new UserException("Invalid user!");
//        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        String accessToken = jwtService.generateTokenUser(data);
        String refreshToken = refreshTokenService.createRefreshTokenUser(data).getToken();

        LoginResponseDTO response = new LoginResponseDTO();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);

        return response;
    }

    @Override
    public RegisterResponseDTO register(RegisterUserRequestDTO request) {
        User currData = userRepository.findByEmail(request.getEmail());

        if (currData != null) {
            throw new UserException("Email already exist!");
        }

        Role role = roleRepository.findByName("USER");

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setPin(passwordEncoder.encode(request.getPin()));
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setIsCooldown(false);
        newUser.setRole(role);
        newUser.setCreatedAt(new Date());
        userRepository.save(newUser);

        UserLimitRequestDTO userLimitRequest = new UserLimitRequestDTO();
        userLimitRequest.setUserId(newUser.getId());
        UserLimitResponseDTO userLimitResponseDTO = userLimitService.addUserLimit(userLimitRequest);

        RegisterResponseDTO response = new RegisterResponseDTO();
        response.setStatus(200);
        response.setMessage("Register success!");

        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void userIsCooldownChecker() {
        userRepository.findUsersWithExpiredWithdrawalsAndCooldown();
    }
}