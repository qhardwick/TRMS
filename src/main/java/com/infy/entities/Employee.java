package com.infy.entities;

import com.infy.constants.EmployeeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Table("Employee")
public class Employee {

    @PrimaryKey
    private String username;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    private String email;

    private String supervisor;

    private String department;

    @Column("BenCo")
    private String benefitsCoordinator;

    private double allowance;

    private double pending;

    private double awarded;

    private List<UUID> inbox;

    private List<UUID> requests;

    @Column("role")
    private EmployeeType employeeType;

    public Employee() {
        super();
        this.allowance = 1000;
        this.inbox = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.employeeType = EmployeeType.EMPLOYEE;
    }


}
