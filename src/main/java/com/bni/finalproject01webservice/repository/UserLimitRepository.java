package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.UserLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface UserLimitRepository extends JpaRepository<UserLimit,UUID> {

    UserLimit findByUserId(UUID id);

    @Modifying
    @Transactional
    @Query("""
            update
                UserLimit ul
            set
                ul.limitAccumulation = 100000,
                ul.updatedAt = current_timestamp
            """)
    void resetLimit();
}
