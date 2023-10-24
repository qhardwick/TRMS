package com.infy.entities;

import com.infy.constants.Event;
import com.infy.constants.GradingFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Table
public class Form {

    @PrimaryKey
    private UUID id;

    private String username;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    private String email;

    @Column("event_type")
    private Event eventType;

    @Column("grade_format")
    private GradingFormat gradeFormat;

    private String description;

    @Column("event_date")
    private LocalDate eventDate;

    @Column("event_time")
    private LocalTime eventTime;

    @Column("event_location")
    private String location;

    private String justification;

    private double cost;

    private double reimbursement;

    @Column("days_missed")
    private int daysMissed;

    private String attachment;

    @Column("supervisor_approval")
    private String supervisorApproval;

    @Column("department_head_approval")
    private String departmentHeadApproval;

    private boolean urgent;

    public Form() {
        super();
        this.id = UUID.randomUUID();
    }
}
