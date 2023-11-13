package com.infy.services;

import com.infy.dtos.EmployeeDto;
import com.infy.dtos.FormDto;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface EmployeeService {

    // Add new Employee:
    Mono<EmployeeDto> addEmployee(EmployeeDto employeeDto);

    // View Employee:
    Mono<EmployeeDto> findByUsername(String username);

    // Update Employee:
    Mono<EmployeeDto> updateEmployee(String username, EmployeeDto updatedEmployeeDto);

    // Delete Employee:
    Mono<Void> deleteEmployee(String username);

    // Login:
    Mono<EmployeeDto> login(String username);

    // Submit Request:
    Mono<FormDto> submitRequest(String username, FormDto requestForm);

    // Approve Request:
    Mono<Void> approveRequest(String username, UUID requestId);
}
