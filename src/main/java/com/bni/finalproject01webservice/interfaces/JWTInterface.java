package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.model.Employee;
import com.bni.finalproject01webservice.model.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;

public interface JWTInterface {

    String generateTokenUser(User user);

    String generateTokenEmployee(Employee employee);

    Claims extractAllClaims(String token);

    SecretKey getJWTKey();

    Date extractExpiration(String token);

    Boolean validateToken(String token, UserDetails userDetails);
}
