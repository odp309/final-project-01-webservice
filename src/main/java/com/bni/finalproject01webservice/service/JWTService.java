package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.interfaces.JWTInterface;
import com.bni.finalproject01webservice.model.Employee;
import com.bni.finalproject01webservice.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
public class JWTService implements JWTInterface {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Override
    public String generateTokenUser(User user) {
        Instant now = Instant.now();
//        Instant expirationTime = now.plusSeconds(24 * 60 * 60); // 1 Day
        Instant expirationTime = now.plusSeconds(600); // 10 Min

        return Jwts
                .builder()
                .subject(user.getEmail())
                .claim("id", user.getId())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("role", user.getRole())
                .expiration(Date.from(expirationTime))
                .signWith(getJWTKey())
                .compact();
    }

    @Override
    public String generateTokenEmployee(Employee employee) {
        Instant now = Instant.now();
//        Instant expirationTime = now.plusSeconds(24 * 60 * 60); // 1 Day
        Instant expirationTime = now.plusSeconds(600); // 10 Min

        return Jwts
                .builder()
                .subject(employee.getEmail())
                .claim("id", employee.getId())
                .claim("firstName", employee.getFirstName())
                .claim("lastName", employee.getLastName())
                .claim("role", employee.getRole())
                .claim("branch", employee.getBranch().getName())
                .expiration(Date.from(expirationTime))
                .signWith(getJWTKey())
                .compact();
    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getJWTKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public SecretKey getJWTKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractAllClaims(token).getSubject();
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
