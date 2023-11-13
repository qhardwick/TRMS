package com.infy.controllers;

import com.infy.aspects.Admin;
import com.infy.dtos.DepartmentDto;
import com.infy.services.DepartmentService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/trms/departments")
@OpenAPIDefinition(info = @Info(title = "TRMS Department API", version = "1.0", description = "TRMS Department Information"))
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // Add new Department:
    @Admin
    @PostMapping
    public Mono<ResponseEntity<DepartmentDto>> addDepartment(@Valid @RequestBody DepartmentDto newDepartmentDto, WebSession session) {
        return departmentService.addDepartment(newDepartmentDto)
                .map(departmentDto -> ResponseEntity.created(URI.create("/departments/" + departmentDto.getName())).body(departmentDto))
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().build()));
    }

    // Find Department by name:
    @Admin
    @GetMapping("/{name}")
    public Mono<ResponseEntity<DepartmentDto>> findDepartmentByName(@PathVariable("name") String name, WebSession session) {
        return departmentService.findDepartmentByName(name)
                .map(department -> ResponseEntity.ok(department))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    // Update Department by name:
    @Admin
    @PutMapping("/{name}")
    public Mono<ResponseEntity<DepartmentDto>> updateDepartmentByName(@PathVariable("name") String name, @Valid @RequestBody DepartmentDto updatedDepartmentDto, WebSession session) {
        return departmentService.updateDepartment(name, updatedDepartmentDto)
                .map(departmentDto -> ResponseEntity.ok(departmentDto))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    // Delete Department by name:
    @Admin
    @DeleteMapping("/{name}")
    public Mono<ResponseEntity<Void>> deleteDepartmentByName(@PathVariable("name") String name, WebSession session) {
        return departmentService.deleteDepartment(name)
                .map(deletedDepartment -> ResponseEntity.noContent().build());
    }
}
