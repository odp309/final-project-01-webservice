package com.bni.finalproject01webservice.configuration;

import com.bni.finalproject01webservice.configuration.exceptions.JwtAuthenticationException;
import com.bni.finalproject01webservice.utility.ProblemDetailToJson;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ProblemDetailToJson problemDetailToJson;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        ProblemDetail errorDetail;
        Throwable cause = (Throwable) request.getAttribute("javax.servlet.error.exception");

        if (cause instanceof JwtAuthenticationException && cause.getCause() instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, cause.getMessage());
            errorDetail.setProperty("access_denied_reason", "Expired JWT token");
        } else if (cause instanceof JwtAuthenticationException && cause.getCause() instanceof JwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, cause.getMessage());
            errorDetail.setProperty("access_denied_reason", "Invalid JWT token");
        } else if (cause instanceof JwtAuthenticationException && cause.getCause() instanceof IllegalArgumentException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, cause.getMessage());
            errorDetail.setProperty("access_denied_reason", "Illegal JWT token");
        } else {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "Authentication required");
            errorDetail.setProperty("access_denied_reason", "Authentication Failure");
        }

        String jsonErrorDetail = problemDetailToJson.convert(errorDetail);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonErrorDetail);
    }
}
