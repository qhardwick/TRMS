package com.infy.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infy.constants.EmployeeType;
import com.infy.entities.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Valid
public class EmployeeDto {

    @NotEmpty(message = "employee.username.must")
    private String username;

    @NotEmpty(message = "employee.firstName.must")
    private String firstName;

    @NotEmpty(message = "employee.lastName.must")
    private String lastName;

    @NotEmpty(message = "employee.email.must")
    @Email(message = "employee.email.invalid")
    private String email;

    private String supervisor;
    private String departmentHead;
    private String benefitsCoordinator;
    private double allowance;
    private double pending;
    private double awarded;
    private List<UUID> awaitingMyApproval;
    private List<UUID> needsAttention;
    private EmployeeType employeeType;

    public EmployeeDto() {
        super();
        this.allowance = 1000;
        this.awaitingMyApproval = new ArrayList<>();
        this.needsAttention = new ArrayList<>();
        this.employeeType = EmployeeType.EMPLOYEE;
    }

    public EmployeeDto(Employee employee) {
        this();
        this.username = employee.getUsername();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.email = employee.getEmail();
        this.supervisor = employee.getSupervisor();
        this.departmentHead = employee.getDepartmentHead();
        this.benefitsCoordinator = employee.getBenefitsCoordinator();
        this.allowance = employee.getAllowance();
        this.pending = employee.getPending();
        this.awarded = employee.getAwarded();
        this.awaitingMyApproval = employee.getAwaitingMyApproval();
        this.needsAttention = employee.getNeedsAttention();
        this.employeeType = employee.getEmployeeType();
    }

    @JsonIgnore
    public Employee getEmployee() {
        return new Employee(
                this.username,
                this.firstName,
                this.lastName,
                this.email,
                this.supervisor,
                this.departmentHead,
                this.benefitsCoordinator,
                this.allowance,
                this.pending,
                this.awarded,
                this.awaitingMyApproval,
                this.needsAttention,
                this.employeeType
        );
    }
}