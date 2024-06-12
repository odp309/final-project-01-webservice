package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);

    @Query("""
            select
                u
            from
                User u
            where
                u.id IN :ids
            """)
    List<User> findUsersByIds(@Param("ids") List<UUID> ids);

    @Modifying
    @Transactional
    @Query("""
            update
                User u
            set
                u.isCooldown = true,
                u.updatedAt = current_timestamp
            where
                u.id = :id
            """)
    void updateIsCooldownToTrue(@Param("id") UUID id);

    @Modifying
    @Transactional
    @Query("""
            update
                User u
            set
                u.isCooldown = false,
                u.updatedAt = current_timestamp
            where
                u.id = :id
            """)
    void updateIsCooldownToFalse(@Param("id") UUID id);
}
