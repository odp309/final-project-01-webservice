package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.model.Employee;
import com.bni.finalproject01webservice.model.User;
import com.bni.finalproject01webservice.repository.EmployeeRepository;
import com.bni.finalproject01webservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Setter
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private String UDSRole;

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        if (this.UDSRole.equals("USER")) {
            User user = userRepository.findByEmail(email);

            if (user != null) {
                return new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        Collections.singleton(getAuthorityUser(user))
                );
            } else {
                throw new UsernameNotFoundException("User not found with email: " + email);
            }
        } else {
            Employee employee = employeeRepository.findByEmail(email);

            if (employee != null) {
                return new org.springframework.security.core.userdetails.User(
                        employee.getEmail(),
                        employee.getPassword(),
                        Collections.singleton(getAuthorityEmployee(employee))
                );
            } else {
                throw new UsernameNotFoundException("Employee not found with email: " + email);
            }
        }
    }

    private SimpleGrantedAuthority getAuthorityUser(User user) {
        return new SimpleGrantedAuthority("ROLE_" + user.getRole().getName());
    }

    private SimpleGrantedAuthority getAuthorityEmployee(Employee employee) {
        return new SimpleGrantedAuthority("ROLE_" + employee.getRole().getName());
    }
}
