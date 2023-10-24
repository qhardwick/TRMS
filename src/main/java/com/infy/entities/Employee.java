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

    @Column("department_head")
    private String departmentHead;

    @Column("BenCo")
    private String benefitsCoordinator;

    private double allowance;

    private double pending;

    private double awarded;

    @Column("awaiting_approval")
    private List<UUID> awaitingMyApproval;

    @Column("needs_attention")
    private List<UUID> needsAttention;

    @Column("role")
    private EmployeeType employeeType;

    public Employee() {
        super();
        this.allowance = 1000;
        this.awaitingMyApproval = new ArrayList<>();
        this.needsAttention = new ArrayList<>();
        this.employeeType = EmployeeType.EMPLOYEE;
    }


}
