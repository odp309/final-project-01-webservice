package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.configuration.exceptions.UserException;
import com.bni.finalproject01webservice.dto.auth.request.LoginRequestDTO;
import com.bni.finalproject01webservice.dto.employee.request.*;
import com.bni.finalproject01webservice.dto.employee.response.*;
import com.bni.finalproject01webservice.dto.init.response.InitResponseDTO;
import com.bni.finalproject01webservice.dto.auth.response.LoginResponseDTO;
import com.bni.finalproject01webservice.dto.auth.response.RegisterResponseDTO;
import com.bni.finalproject01webservice.interfaces.*;
import com.bni.finalproject01webservice.model.Branch;
import com.bni.finalproject01webservice.model.Employee;
import com.bni.finalproject01webservice.model.PasswordResetToken;
import com.bni.finalproject01webservice.model.Role;
import com.bni.finalproject01webservice.repository.BranchRepository;
import com.bni.finalproject01webservice.repository.EmployeeRepository;
import com.bni.finalproject01webservice.repository.PasswordResetTokenRepository;
import com.bni.finalproject01webservice.repository.RoleRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService implements EmployeeInterface {

    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private final RoleRepository roleRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final JWTInterface jwtService;
    private final RefreshTokenInterface refreshTokenService;
    private final ResourceRequestCheckerInterface resourceRequestCheckerService;
    private final EmailSenderInterface emailSenderService;
    private final PasswordResetTokenInterface passwordResetTokenService;
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
            Branch branch = branchRepository.findById("9007").orElseThrow(() -> new RuntimeException("Branch not found!"));
            Employee employee = new Employee();
            employee.setEmail("admin.manager@bni.co.id");
            employee.setPassword(passwordEncoder.encode("admin"));
            employee.setFirstName("admin");
            employee.setLastName("manager");
            employee.setNip("A000000");
            employee.setIsActive(true);
            employee.setIsPasswordChange(true);
            employee.setBranch(branch);
            employee.setRole(Objects.requireNonNullElse(currAdminMgrRole, adminMgrRole));
            employee.setCreatedBy("SYSTEM");
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

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        Employee data = employeeRepository.findByEmail(request.getEmail());
        if (data.getIsActive()) {
            String accessToken = jwtService.generateTokenEmployee(data);
            String refreshToken = refreshTokenService.createRefreshTokenEmployee(data).getToken();

            response.setAccessToken(accessToken);
            response.setRefreshToken(refreshToken);
            if (!data.getIsPasswordChange()) {
                response.setResetToken(passwordResetTokenRepository.findByEmployeeId(data.getId()).getToken());
            }
        } else {
            throw new UserException("Employee is not active!");
        }

        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RegisterResponseDTO registerAdmin(RegisterEmployeeRequestDTO request, HttpServletRequest headerRequest) {

        UUID employeeId = resourceRequestCheckerService.extractIdFromToken(headerRequest);
        String branchCode = resourceRequestCheckerService.extractBranchCodeFromToken(headerRequest);

        Employee currData = employeeRepository.findByEmail(request.getEmail());

        if (currData != null) {
            throw new UserException("Email already exist!");
        }

        Role role = roleRepository.findByName("ADMIN");
        Branch branch = branchRepository.findById(branchCode).orElseThrow(() -> new RuntimeException("Branch not found!"));

        String randomPassword = RandomStringUtils.randomAlphanumeric(10);

        Employee newEmployee = new Employee();
        newEmployee.setEmail(request.getEmail());
        newEmployee.setPassword(passwordEncoder.encode(randomPassword));
        newEmployee.setFirstName(request.getFirstName());
        newEmployee.setLastName(request.getLastName());
        newEmployee.setIsActive(true);
        newEmployee.setIsPasswordChange(false);
        newEmployee.setNip(request.getNip());
        newEmployee.setRole(role);
        newEmployee.setCreatedAt(new Date());
        newEmployee.setBranch(branch);
        newEmployee.setCreatedBy(String.valueOf(employeeId));
        employeeRepository.save(newEmployee);

        String passwordResetToken = passwordResetTokenService.createPasswordResetTokenEmployee(newEmployee).getToken();

        String resetLink = "http://yourdomain.com/reset-password?token=" + passwordResetToken;
        String emailText = "Your account has been created. Your temporary password is: " + randomPassword +
                "\nPlease change your password using the following link: " + resetLink;

        emailSenderService.sendEmail(request.getEmail(), "Account Registration", emailText);

        RegisterResponseDTO response = new RegisterResponseDTO();
        response.setStatus(200);
        response.setMessage("Register success!");

        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RegisterResponseDTO registerTeller(RegisterEmployeeRequestDTO request, HttpServletRequest headerRequest) {

        UUID employeeId = resourceRequestCheckerService.extractIdFromToken(headerRequest);
        String branchCode = resourceRequestCheckerService.extractBranchCodeFromToken(headerRequest);

        Employee currData = employeeRepository.findByEmail(request.getEmail());

        if (currData != null) {
            throw new UserException("Email already exist!");
        }

        Role role = roleRepository.findByName("TELLER");
        Branch branch = branchRepository.findById(branchCode).orElseThrow(() -> new RuntimeException("Branch not found!"));

        String randomPassword = RandomStringUtils.randomAlphanumeric(10);

        Employee newEmployee = new Employee();
        newEmployee.setEmail(request.getEmail());
        newEmployee.setPassword(passwordEncoder.encode(randomPassword));
        newEmployee.setFirstName(request.getFirstName());
        newEmployee.setLastName(request.getLastName());
        newEmployee.setIsActive(true);
        newEmployee.setIsPasswordChange(false);
        newEmployee.setNip(request.getNip());
        newEmployee.setRole(role);
        newEmployee.setCreatedAt(new Date());
        newEmployee.setBranch(branch);
        newEmployee.setCreatedBy(String.valueOf(employeeId));
        employeeRepository.save(newEmployee);

        String passwordResetToken = passwordResetTokenService.createPasswordResetTokenEmployee(newEmployee).getToken();

        String resetLink = "http://yourdomain.com/reset-password?token=" + passwordResetToken;
        String emailText = "Your account has been created. Your temporary password is: " + randomPassword +
                "\nPlease change your password using the following link: " + resetLink;

        emailSenderService.sendEmail(request.getEmail(), "Account Registration", emailText);

        RegisterResponseDTO response = new RegisterResponseDTO();
        response.setStatus(200);
        response.setMessage("Register success!");

        return response;
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployee(GetAllEmployeeRequestDTO request) {
        List<Employee> employees = employeeRepository.findByBranchCode(request.getBranchCode());

        return employees.stream()
                .map(employee -> {
                    EmployeeResponseDTO response = new EmployeeResponseDTO();
                    response.setId(employee.getId());
                    response.setBranchCode(employee.getBranch().getCode());
                    response.setRoleName(employee.getRole().getName());
                    response.setEmail(employee.getEmail());
                    response.setFirstName(employee.getFirstName());
                    response.setLastName(employee.getLastName());
                    response.setNip(employee.getNip());
                    response.setIsActive(employee.getIsActive());
                    response.setCreatedAt(employee.getCreatedAt());
                    response.setUpdatedAt(employee.getUpdatedAt());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ActivateEmployeeResponseDTO activateEmployee(ActivateEmployeeRequestDTO request) {

        ActivateEmployeeResponseDTO response = new ActivateEmployeeResponseDTO();

        Employee employee = employeeRepository.findById(request.getId()).
                orElseThrow(() -> new UserException("Employee Not Found"));

        if (!employee.getIsActive()) {
            employee.setIsActive(true);
            employeeRepository.save(employee);
            response.setMessage("Employee has been activated!");
        } else {
            employee.setIsActive(false);
            employeeRepository.save(employee);
            response.setMessage("Employee has been deactivated!");
        }

        return response;
    }

    //////////////////////////////// VERSION 2.0 BLOCK ////////////////////////////////

    @Override
    public List<EmployeeResponseDTO> getAllEmployee(GetAllEmployeeRequestDTO request, HttpServletRequest headerRequest) {

        String branchCode = resourceRequestCheckerService.extractBranchCodeFromToken(headerRequest);

        List<Employee> employees = employeeRepository.findByBranchCode(branchCode);

        return employees.stream()
                .map(employee -> {
                    EmployeeResponseDTO response = new EmployeeResponseDTO();
                    response.setId(employee.getId());
                    response.setBranchCode(employee.getBranch().getCode());
                    response.setRoleName(employee.getRole().getName());
                    response.setEmail(employee.getEmail());
                    response.setFirstName(employee.getFirstName());
                    response.setLastName(employee.getLastName());
                    response.setNip(employee.getNip());
                    response.setIsActive(employee.getIsActive());
                    response.setCreatedAt(employee.getCreatedAt());
                    response.setUpdatedAt(employee.getUpdatedAt());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PasswordResetEmployeeResponseDTO passwordResetEmployee(PasswordResetEmployeeRequestDTO request) {

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(request.getToken());
        Boolean isExpired = passwordResetTokenService.isPasswordResetTokenExpired(passwordResetToken);

        if (passwordResetToken == null || isExpired) {
            throw new RuntimeException("Invalid or expired token!");
        }

        Employee employee = passwordResetToken.getEmployee();

        employee.setPassword(passwordEncoder.encode(request.getNewPassword()));
        if (!employee.getIsPasswordChange()) {
            employee.setIsPasswordChange(true);
        }
        employee.setUpdatedAt(new Date());
        employeeRepository.save(employee);

        passwordResetTokenRepository.deleteToken(passwordResetToken.getId());

        PasswordResetEmployeeResponseDTO response = new PasswordResetEmployeeResponseDTO();
        response.setStatus(200);
        response.setMessage("Change password success!");

        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InvokePasswordResetEmployeeResponseDTO invokePasswordResetEmployee(InvokePasswordResetEmployeeRequestDTO request) {

        Employee employee = employeeRepository.findById(request.getEmployeeId()).orElseThrow(() -> new UserException("User not found!"));

        String randomPassword = RandomStringUtils.randomAlphanumeric(10);

        employee.setPassword(passwordEncoder.encode(randomPassword));
        employee.setUpdatedAt(new Date());
        employeeRepository.save(employee);

        String passwordResetToken = passwordResetTokenService.createPasswordResetTokenEmployee(employee).getToken();

        String resetLink = "http://yourdomain.com/reset-password?token=" + passwordResetToken;
        String emailText = "Your password has been reset. Your temporary password is: " + randomPassword +
                "\nPlease change your password using the following link: " + resetLink;

        emailSenderService.sendEmail(employee.getEmail(), "Password Reset", emailText);

        InvokePasswordResetEmployeeResponseDTO response = new InvokePasswordResetEmployeeResponseDTO();
        response.setStatus(200);
        response.setMessage("Invoke reset password success!");

        return response;
    }
}