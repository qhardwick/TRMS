package com.infy.services;

import com.infy.dtos.DepartmentDto;
import reactor.core.publisher.Mono;

public interface DepartmentService {

    // Add new Department:
    Mono<DepartmentDto> addDepartment(DepartmentDto newDepartmentDto);

    // Find Department by name:
    Mono<DepartmentDto> findDepartmentByName(String name);

    // Update Department by name:
    Mono<DepartmentDto> updateDepartmentByName(String name, DepartmentDto updatedDepartmentDto);

    // Delete Department by name:
    Mono<Void> deleteDepartmentByName(String name);
}
