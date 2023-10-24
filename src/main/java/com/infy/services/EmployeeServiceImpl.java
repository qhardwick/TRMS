package com.infy.services;

import com.infy.dto.EmployeeDto;
import com.infy.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Mono<EmployeeDto> addEmployee(EmployeeDto newEmployeeDto) {
        return employeeRepository.save(newEmployeeDto.getEmployee())
                .map(EmployeeDto::new);
    }

    @Override
    public Mono<EmployeeDto> login(String username) {
        return employeeRepository.findById(username)
                .map(EmployeeDto::new);
    }

    @Override
    public Mono<EmployeeDto> findByUsername(String username) {
        return employeeRepository.findById(username)
                .map(EmployeeDto::new);
    }
}
