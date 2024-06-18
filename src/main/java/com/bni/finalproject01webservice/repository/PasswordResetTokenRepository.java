package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.Employee;
import com.bni.finalproject01webservice.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, UUID> {

    Optional<PasswordResetToken> findByEmployee(Employee employee);

    PasswordResetToken findByToken(String token);
}
