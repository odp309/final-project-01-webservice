package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    Employee findByEmail(String email);
}
