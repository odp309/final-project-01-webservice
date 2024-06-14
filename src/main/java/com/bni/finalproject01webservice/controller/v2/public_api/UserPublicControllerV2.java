package com.bni.finalproject01webservice.controller.v2.public_api;

import com.bni.finalproject01webservice.configuration.exceptions.RefreshTokenException;
import com.bni.finalproject01webservice.dto.auth.request.LoginRequestDTO;
import com.bni.finalproject01webservice.dto.auth.request.RefreshTokenRequestDTO;
import com.bni.finalproject01webservice.dto.user.request.RegisterUserRequestDTO;
import com.bni.finalproject01webservice.dto.auth.response.LoginResponseDTO;
import com.bni.finalproject01webservice.dto.auth.response.RegisterResponseDTO;
import com.bni.finalproject01webservice.interfaces.JWTInterface;
import com.bni.finalproject01webservice.interfaces.RefreshTokenInterface;
import com.bni.finalproject01webservice.interfaces.UserInterface;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/public/user")
@Tag(name = "Public API V2", description = "Public API open to the public")
public class UserPublicControllerV2 {

    private final UserInterface userService;
    private final JWTInterface jwtService;
    private final RefreshTokenInterface refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        try {
            LoginResponseDTO result = userService.login(request);
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            authentication.getPrincipal();
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            throw new BadCredentialsException("Bad Credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterUserRequestDTO request) {
        RegisterResponseDTO result = userService.register(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO request) {
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService
                .findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(refreshToken -> {
                    if (refreshToken.getUser() != null) {
                        String accessToken = jwtService.generateTokenUser(refreshToken.getUser());
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
