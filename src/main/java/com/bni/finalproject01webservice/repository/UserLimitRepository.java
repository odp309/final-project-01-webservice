package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.UserLimit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserLimitRepository extends JpaRepository<UserLimit,UUID> {

    UserLimit findByUserId(UUID id);

}
