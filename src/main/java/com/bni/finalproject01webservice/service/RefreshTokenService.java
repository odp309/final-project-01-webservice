package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.configuration.exceptions.RefreshTokenExpiredException;
import com.bni.finalproject01webservice.interfaces.RefreshTokenInterface;
import com.bni.finalproject01webservice.model.Employee;
import com.bni.finalproject01webservice.model.RefreshToken;
import com.bni.finalproject01webservice.model.User;
import com.bni.finalproject01webservice.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService implements RefreshTokenInterface {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken createRefreshTokenUser(User user) {
        Optional<RefreshToken> existingRefreshToken = refreshTokenRepository.findByUser(user);
        existingRefreshToken.ifPresent(refreshTokenRepository::delete);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(86400000)); // 1 Day
        refreshToken.setUser(user);
        user.setRefreshToken(refreshToken);
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken createRefreshTokenEmployee(Employee employee) {
        Optional<RefreshToken> existingRefreshToken = refreshTokenRepository.findByEmployee(employee);
        existingRefreshToken.ifPresent(refreshTokenRepository::delete);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(86400000)); // 1 Day
        refreshToken.setEmployee(employee);
        employee.setRefreshToken(refreshToken);
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenExpiredException(token.getToken() + " refresh token was expired. Please make a new sign in request.");
        }
        return token;
    }
}
