package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.interfaces.PasswordResetTokenInterface;
import com.bni.finalproject01webservice.model.Employee;
import com.bni.finalproject01webservice.model.PasswordResetToken;
import com.bni.finalproject01webservice.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetTokenService implements PasswordResetTokenInterface {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PasswordResetToken createPasswordResetTokenEmployee(Employee employee) {

        Optional<PasswordResetToken> existingPasswordResetToken = passwordResetTokenRepository.findByEmployee(employee);

        existingPasswordResetToken.ifPresent(token -> {
            passwordResetTokenRepository.delete(token);
            passwordResetTokenRepository.flush(); // force deletion
        });

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(UUID.randomUUID().toString());
        passwordResetToken.setExpiryDate(Instant.now().plusMillis(86400000)); // 1 Day
        passwordResetToken.setEmployee(employee);
        employee.setPasswordResetToken(passwordResetToken);

        return passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public Boolean isPasswordResetTokenExpired(PasswordResetToken passwordResetToken) {

        if (passwordResetToken.getExpiryDate().isBefore(Instant.now())) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return true;
        }

        return false;
    }
}
