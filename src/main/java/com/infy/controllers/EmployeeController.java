package com.infy.controllers;

import com.infy.aspects.Admin;
import com.infy.aspects.Benco;
import com.infy.aspects.CurrentUser;
import com.infy.aspects.LoggedIn;
import com.infy.dtos.EmployeeDto;
import com.infy.dtos.FormDto;
import com.infy.services.EmployeeService;
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
@RequestMapping("/trms/employees")
@OpenAPIDefinition(info = @Info(title = "TRMS Employee API", version = "1.0", description = "TRMS Employee Information"))
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Add new Employee:
    @Admin
    @PostMapping
    public Mono<ResponseEntity<EmployeeDto>> addEmployee(@Valid @RequestBody EmployeeDto newEmployeeDto, WebSession session) {
        return employeeService.addEmployee(newEmployeeDto)
                .map(newEmployee -> ResponseEntity.created(URI.create("/employees/" + newEmployee.getUsername())).body(newEmployee))
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().build()));
    }

    // View Employee:
    @LoggedIn
    @GetMapping("/{username}")
    public Mono<ResponseEntity<EmployeeDto>> findByUsername(@PathVariable("username") String username, WebSession session) {
        return employeeService.findByUsername(username)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    // Update Employee:
    @Benco
    @PutMapping("/{username}")
    public Mono<ResponseEntity<EmployeeDto>> updateEmployee(@PathVariable("username") String username, @RequestBody EmployeeDto updatedEmployeeDto, WebSession session) {
        return employeeService.updateEmployee(username, updatedEmployeeDto)
                .map(updatedEmployee -> ResponseEntity.ok(updatedEmployee))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    // Delete Employee:
    //@Benco
    @DeleteMapping("/{username}")
    public Mono<ResponseEntity<Void>> deleteEmployee(@PathVariable("username") String username, WebSession session) {
        return employeeService.deleteEmployee(username)
                .map(deletedEmployee -> ResponseEntity.noContent().build());
    }

    // Login:
    @PostMapping("/login")
    public Mono<ResponseEntity<EmployeeDto>> login(@RequestParam String username, WebSession session) {
        Mono<EmployeeDto> loggedUser = employeeService.findByUsername(username);
        return loggedUser
                .map(employee -> {
                    session.getAttributes().put("loggedUser", employee);
                    return ResponseEntity.ok(employee);
                }).switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    // Logout:
    @DeleteMapping("/logout")
    public Mono<ResponseEntity<Void>> logout(WebSession session) {
        session.invalidate();
        return Mono.just(ResponseEntity.noContent().build());
    }

    // Submit Reimbursement Request:
    @CurrentUser
    @PostMapping("/{username}/requests")
    public Mono<ResponseEntity<FormDto>> submitRequest(@PathVariable("username") String username, @Valid @RequestBody FormDto requestForm, WebSession session) {
        return employeeService.submitRequest(username, requestForm)
                .map(submittedForm -> ResponseEntity.created(URI.create("/employees/" + username + "/requests/" + requestForm.getId())).body(submittedForm))
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().build()));
    }
}
