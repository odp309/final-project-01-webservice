package com.bni.finalproject01webservice.configuration;

import com.bni.finalproject01webservice.configuration.exceptions.JwtAuthenticationException;
import com.bni.finalproject01webservice.interfaces.JWTInterface;
import com.bni.finalproject01webservice.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTInterface jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    @Qualifier("requestMappingHandlerMapping")
    private HandlerMapping handlerMapping;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            handleLoginWithUserDetailsAuth(request);
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authHeader.substring(7);
            Claims claims = jwtService.extractAllClaims(token);
            String email = claims.getSubject();
            @SuppressWarnings("unchecked")
            Map<String, Object> role = (Map<String, Object>) claims.get("role");

            customUserDetailsService.setUDSRole("");
            if ("USER".equals(role.get("name"))) {
                customUserDetailsService.setUDSRole("USER");
            } else {
                customUserDetailsService.setUDSRole("EMPLOYEE");
            }

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (ExpiredJwtException e) {
            request.setAttribute("javax.servlet.error.exception", new JwtAuthenticationException("JWT token has expired", e));
        } catch (JwtException e) {
            request.setAttribute("javax.servlet.error.exception", new JwtAuthenticationException("Invalid JWT token", e));
        } catch (IllegalArgumentException e) {
            request.setAttribute("javax.servlet.error.exception", new JwtAuthenticationException("Unable to get JWT token", e));
        }

        filterChain.doFilter(request, response);
    }

    private void handleLoginWithUserDetailsAuth(HttpServletRequest request) {
        try {
            HandlerExecutionChain handlerExecutionChain = handlerMapping.getHandler(request);
            if (handlerExecutionChain != null) {
                Object handler = handlerExecutionChain.getHandler();
                if (handler instanceof HandlerMethod handlerMethod) {
                    String controllerName = handlerMethod.getBeanType().getSimpleName();
                    String methodName = handlerMethod.getMethod().getName();
                    if (("EmployeePublicController".equals(controllerName) || "EmployeePublicControllerV2".equals(controllerName)) && "login".equals(methodName)) {
                        customUserDetailsService.setUDSRole("EMPLOYEE");
                    } else if (("UserPublicController".equals(controllerName) || "UserPublicControllerV2".equals(controllerName)) && "login".equals(methodName)) {
                        customUserDetailsService.setUDSRole("USER");
                    } else {
                        customUserDetailsService.setUDSRole("");
                    }
                }
            }
        } catch (Exception e) {
            Logger logger = Logger.getLogger(JWTAuthFilter.class.getName());
            logger.log(Level.SEVERE, "An exception occurred in JWTAuthFilter: " + e.getMessage(), e);
        }
    }
}
