package com.infy.dto;

import com.infy.entities.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@Valid
public class DepartmentDto {

    @NotEmpty(message = "department.name.must")
    private String name;

    @NotEmpty(message = "department.head.must")
    private String head;

    public DepartmentDto() {
        super();
    }

    public DepartmentDto(Department department) {
        this();
        this.name = department.getName();
        this.head = department.getHead();
    }

    @JsonIgnore
    public Department getDepartment() {
        return new Department(this.name, this.head);
    }
}
