package com.bni.finalproject01webservice.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JWTAuthFilter jwtAuthFilter;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
        return authManagerBuilder.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v2/private/employee/for-admin-mgr", "/api/v1/private/employee/for-admin-mgr/**").hasRole("ADMIN_MGR")
                        .requestMatchers("/api/v2/private/employee/for-admin", "/api/v1/private/employee/for-admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/v2/private/employee/for-teller", "/api/v1/private/employee/for-teller/**").hasRole("TELLER")
                        .requestMatchers("/api/v2/private/user/**").hasRole("USER")
                        .requestMatchers("/api/v2/private/**").authenticated()
                        .requestMatchers("/api/v1/private/employee/for-admin-mgr", "/api/v1/private/employee/for-admin-mgr/**").hasRole("ADMIN_MGR")
                        .requestMatchers("/api/v1/private/employee/for-admin", "/api/v1/private/employee/for-admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/private/employee/for-teller", "/api/v1/private/employee/for-teller/**").hasRole("TELLER")
                        .requestMatchers("/api/v1/private/user/**").hasRole("USER")
                        .requestMatchers("/api/v1/private/**").authenticated()
                        .requestMatchers("/api/v1/public/**").permitAll()
                        .requestMatchers("/ws-exchange-rate/**").permitAll()
                        .requestMatchers(openSwaggerResources()).permitAll()
                        .anyRequest().denyAll()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private String[] openSwaggerResources() {
        return new String[]{
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/v3/api-docs",
                "/v3/api-docs/**"
        };
    }
}
