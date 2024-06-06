package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.configuration.exceptions.UserException;
import com.bni.finalproject01webservice.dto.auth.request.LoginRequestDTO;
import com.bni.finalproject01webservice.dto.employee.request.DataEmployeeRequestDTO;
import com.bni.finalproject01webservice.dto.employee.request.RegisterEmployeeRequestDTO;
import com.bni.finalproject01webservice.dto.employee.response.DataEmployeeResponseDTO;
import com.bni.finalproject01webservice.dto.init.response.InitResponseDTO;
import com.bni.finalproject01webservice.dto.auth.response.LoginResponseDTO;
import com.bni.finalproject01webservice.dto.auth.response.RegisterResponseDTO;
import com.bni.finalproject01webservice.interfaces.EmployeeInterface;
import com.bni.finalproject01webservice.interfaces.JWTInterface;
import com.bni.finalproject01webservice.interfaces.RefreshTokenInterface;
import com.bni.finalproject01webservice.model.Employee;
import com.bni.finalproject01webservice.model.Role;
import com.bni.finalproject01webservice.repository.EmployeeRepository;
import com.bni.finalproject01webservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService implements EmployeeInterface {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final JWTInterface jwtService;
    private final RefreshTokenInterface refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InitResponseDTO initRoleAndEmployee() {
        Employee currEmployee = employeeRepository.findByEmail("admin.manager@bni.co.id");
        Role currAdminMgrRole = roleRepository.findByName("ADMIN_MGR");
        Role currAdminRole = roleRepository.findByName("ADMIN");
        Role currTellerRole = roleRepository.findByName("TELLER");
        Role currUserRole = roleRepository.findByName("USER");

        Role adminMgrRole = new Role();
        Role adminRole = new Role();
        Role tellerRole = new Role();
        Role userRole = new Role();

        String status = "";

        if (currAdminMgrRole == null) {
            adminMgrRole.setName("ADMIN_MGR");
            adminMgrRole.setDescription("Admin manager role, handle admin CRUD operations.");
            adminMgrRole.setCreatedAt(new Date());
            roleRepository.save(adminMgrRole);
            status += "Admin manager role has been initialized!";
        }

        if (currAdminRole == null) {
            adminRole.setName("ADMIN");
            adminRole.setDescription("Admin role, has control over a branch.");
            adminRole.setCreatedAt(new Date());
            roleRepository.save(adminRole);
            status += "Admin role has been initialized!";
        }

        if (currTellerRole == null) {
            tellerRole.setName("TELLER");
            tellerRole.setDescription("Teller role, handle front-liner matters.");
            tellerRole.setCreatedAt(new Date());
            roleRepository.save(tellerRole);
            status += "Teller role has been initialized!";
        }

        if (currUserRole == null) {
            userRole.setName("USER");
            userRole.setDescription("User role, ya user bos.");
            userRole.setCreatedAt(new Date());
            roleRepository.save(userRole);
            status += "User role has been initialized!";
        }

        if (currEmployee == null) {
            Employee employee = new Employee();
            employee.setEmail("admin.manager@bni.co.id");
            employee.setPassword(passwordEncoder.encode("admin"));
            employee.setFirstName("admin");
            employee.setLastName("manager");
            employee.setNip("A000000");
            employee.setIsActive(true);
            employee.setRole(Objects.requireNonNullElse(currAdminMgrRole, adminMgrRole));
            employee.setCreatedAt(new Date());
            employeeRepository.save(employee);
            status += "Admin manager account has been initialized!";
        }

        InitResponseDTO response = new InitResponseDTO();
        response.setMessage(
                status.length() >= 179 ?
                        "All roles and accounts has been initialized!" :
                        status.isEmpty() ?
                                "All roles and accounts already initialized!" :
                                status);

        return response;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        LoginResponseDTO response = new LoginResponseDTO();
        Employee data = employeeRepository.findByEmail(request.getEmail());

        if (data.getIsActive()) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            String accessToken = jwtService.generateTokenEmployee(data);
            String refreshToken = refreshTokenService.createRefreshTokenEmployee(data).getToken();

            response.setAccessToken(accessToken);
            response.setRefreshToken(refreshToken);
        } else {
            throw new UserException("Employee is not active!");
        }

        return response;
    }

    @Override
    public RegisterResponseDTO registerAdmin(RegisterEmployeeRequestDTO request) {
        Employee currData = employeeRepository.findByEmail(request.getEmail());

        if (currData != null) {
            throw new UserException("Email already exist!");
        }

        Role role = roleRepository.findByName("ADMIN");

        Employee newEmployee = new Employee();
        newEmployee.setEmail(request.getEmail());
        newEmployee.setPassword(passwordEncoder.encode(request.getPassword()));
        newEmployee.setFirstName(request.getFirstName());
        newEmployee.setLastName(request.getLastName());
        newEmployee.setIsActive(true);
        newEmployee.setNip(request.getNip());
        newEmployee.setRole(role);
        newEmployee.setCreatedAt(new Date());

        employeeRepository.save(newEmployee);

        RegisterResponseDTO response = new RegisterResponseDTO();
        response.setStatus(200);
        response.setMessage("Register success!");

        return response;
    }

    @Override
    public RegisterResponseDTO registerTeller(RegisterEmployeeRequestDTO request) {
        Employee currData = employeeRepository.findByEmail(request.getEmail());

        if (currData != null) {
            throw new UserException("Email already exist!");
        }

        Role role = roleRepository.findByName("TELLER");

        Employee newEmployee = new Employee();
        newEmployee.setEmail(request.getEmail());
        newEmployee.setPassword(passwordEncoder.encode(request.getPassword()));
        newEmployee.setFirstName(request.getFirstName());
        newEmployee.setLastName(request.getLastName());
        newEmployee.setIsActive(true);
        newEmployee.setNip(request.getNip());
        newEmployee.setRole(role);
        newEmployee.setCreatedAt(new Date());

        employeeRepository.save(newEmployee);

        RegisterResponseDTO response = new RegisterResponseDTO();
        response.setStatus(200);
        response.setMessage("Register success!");

        return response;
    }

    @Override
    public DataEmployeeResponseDTO getAllEmployee(DataEmployeeRequestDTO request) {
        List<Employee> employees = employeeRepository.findByBranchName(request.getBranchName());

        DataEmployeeResponseDTO response = new DataEmployeeResponseDTO();
        response.setEmployee(employees.stream()
                .map(employee -> {
                    DataEmployeeResponseDTO.GetAllEmployee data = new DataEmployeeResponseDTO.GetAllEmployee();

                    data.setId(employee.getId());
                    data.setCreatedAt(employee.getCreatedAt());
                    data.setEmail(employee.getEmail());
                    data.setFirstName(employee.getFirstName());
                    data.setIsActive(employee.getIsActive());
                    data.setLastName(employee.getLastName());
                    data.setNip(employee.getNip());
                    data.setUpdatedAt(employee.getUpdatedAt());
                    data.setBranchId(employee.getBranch().getId());
                    data.setRoleId(employee.getRole().getId());
                    return data;
                })
                .collect(Collectors.toList()));

        return response;
    }


}