package com.infy.services;

import com.infy.dtos.DepartmentDto;
import com.infy.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    // Add new Department:
    @Override
    public Mono<DepartmentDto> addDepartment(DepartmentDto newDepartmentDto) {
        return departmentRepository.save(newDepartmentDto.getDepartment()).map(department -> new DepartmentDto(department));
    }

    // Find Department by name:
    @Override
    public Mono<DepartmentDto> findDepartmentByName(String name) {
        return departmentRepository.findById(name).map(department -> new DepartmentDto(department));
    }

    // Update Department by name:
    @Override
    public Mono<DepartmentDto> updateDepartmentByName(String name, DepartmentDto updatedDepartmentDto) {
        return departmentRepository.findById(name).flatMap(department -> {

            if(updatedDepartmentDto.getName() != null)
                department.setName(updatedDepartmentDto.getName());
            if(updatedDepartmentDto.getHead() != null) {
                department.setHead(updatedDepartmentDto.getHead());
            }

            return departmentRepository.save(department);
        }).map(updatedDepartment -> new DepartmentDto(updatedDepartment));
    }

    // Delete Department by name:
    @Override
    public Mono<Void> deleteDepartmentByName(String name) {
        return departmentRepository.deleteById(name);
    }
}
