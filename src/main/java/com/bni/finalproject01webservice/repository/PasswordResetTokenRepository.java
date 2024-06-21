package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.Employee;
import com.bni.finalproject01webservice.model.PasswordResetToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, UUID> {

    Optional<PasswordResetToken> findByEmployee(Employee employee);

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByEmployeeId(UUID employeeId);

    @Modifying
    @Query("""
            delete from
                PasswordResetToken prt
            where
                prt.id = :id
            """)
    void deleteToken(@Param("id") UUID id);
}
