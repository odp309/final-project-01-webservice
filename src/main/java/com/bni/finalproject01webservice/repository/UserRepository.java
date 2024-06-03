package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);



}
