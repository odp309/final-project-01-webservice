package com.bni.finalproject01webservice.configuration;

import com.bni.finalproject01webservice.utility.ProblemDetailToJson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
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
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ProblemDetail errorDetail = null;

        errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), "Authentication required");
        errorDetail.setProperty("access_denied_reason", "Authentication Failure");

        String jsonErrorDetail = problemDetailToJson.convert(errorDetail);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonErrorDetail);
    }
}
