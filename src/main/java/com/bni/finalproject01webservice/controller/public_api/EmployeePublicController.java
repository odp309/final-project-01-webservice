package com.bni.finalproject01webservice.controller.public_api;

import com.bni.finalproject01webservice.configuration.exceptions.RefreshTokenException;
import com.bni.finalproject01webservice.dto.*;
import com.bni.finalproject01webservice.interfaces.EmployeeInterface;
import com.bni.finalproject01webservice.interfaces.JWTInterface;
import com.bni.finalproject01webservice.interfaces.RefreshTokenInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/public/employee")
public class EmployeePublicController {

    private final EmployeeInterface employeeService;
    private final JWTInterface jwtService;
    private final RefreshTokenInterface refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        try {
            LoginResponseDTO result = employeeService.login(request);
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            throw new BadCredentialsException("Bad Credentials");
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO request) {
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService
                .findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(refreshToken -> {
                    if (refreshToken.getEmployee() != null) {
                        String accessToken = jwtService.generateTokenEmployee(refreshToken.getEmployee());
                        LoginResponseDTO response = new LoginResponseDTO();
                        response.setAccessToken(accessToken);
                        response.setRefreshToken(requestRefreshToken);
                        return ResponseEntity.ok(response);
                    }
                    throw new RefreshTokenException(requestRefreshToken + " refresh token is not valid!");
                })
                .orElseThrow(() -> new RefreshTokenException(requestRefreshToken + " refresh token is not valid!"));
    }
}
