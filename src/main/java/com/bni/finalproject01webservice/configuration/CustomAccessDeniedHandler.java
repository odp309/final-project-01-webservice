package com.bni.finalproject01webservice.configuration;

import com.bni.finalproject01webservice.utility.ProblemDetailToJson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ProblemDetailToJson problemDetailToJson;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ProblemDetail errorDetail = null;

        errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), "Unauthorized role");
        errorDetail.setProperty("access_denied_reason", "Not Authorized");

        String jsonErrorDetail = problemDetailToJson.convert(errorDetail);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonErrorDetail);
    }
}
