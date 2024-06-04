package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.Employee;
import com.bni.finalproject01webservice.model.RefreshToken;
import com.bni.finalproject01webservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(User user);

    Optional<RefreshToken> findByEmployee(Employee employee);
}
