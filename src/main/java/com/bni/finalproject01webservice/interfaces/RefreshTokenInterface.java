package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.model.Employee;
import com.bni.finalproject01webservice.model.RefreshToken;
import com.bni.finalproject01webservice.model.User;

import java.util.Optional;

public interface RefreshTokenInterface {

    RefreshToken createRefreshTokenUser(User user);

    RefreshToken createRefreshTokenEmployee(Employee employee);

    Optional<RefreshToken> findByToken(String token);

    RefreshToken verifyExpiration(RefreshToken token);
}
