package com.infy.services;

import com.infy.dto.EmployeeDto;
import reactor.core.publisher.Mono;

public interface EmployeeService {
    Mono<EmployeeDto> addEmployee(EmployeeDto employeeDto);

    Mono<EmployeeDto> login(String username);

    Mono<EmployeeDto> findByUsername(String username);
}
