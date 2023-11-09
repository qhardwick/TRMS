package com.infy.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infy.constants.Event;
import com.infy.constants.GradingFormat;
import com.infy.entities.Form;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Valid
public class FormDto {

    private UUID id;


    private String username;
    private String firstName;
    private String lastName;
    private String email;

    @NotEmpty(message = "form.type.must")
    private Event eventType;

    @NotEmpty(message = "form.format.must")
    private GradingFormat gradeFormat;

    @NotEmpty(message = "form.description.must")
    private String description;
     
    @NotEmpty(message = "form.date.must")
    @FutureOrPresent(message = "form.date.future")
    private LocalDate eventDate;

    @NotEmpty(message = "form.time.must")
    private LocalTime eventTime;

    @NotEmpty(message = "form.location.must")
    private String location;

    @NotEmpty(message = "form.justification.must")
    private String justification;

    @NotEmpty(message = "form.cost.must")
    @Positive(message = "form.cost.positive")
    private double cost;

    private double reimbursement;
    private int daysMissed;

    @Pattern(regexp = "([a-zA-Z0-9\\s_\\\\.\\-\\(\\):])+(.pdf|.png|.jpeg|.jpg|.txt|.doc)$", message = "form.attachment.invalid")
    private String attachment;

    @Pattern(regexp = "([a-zA-Z0-9\\s_\\\\.\\-\\(\\):])+(.msg)$", message = "form.supervisorAttachment.invalid")
    private String supervisorApproval;

    @Pattern(regexp = "([a-zA-Z0-9\\s_\\\\.\\-\\(\\):])+(.msg)$", message = "form.departmentHeadAttachment.invalid")
    private String departmentHeadApproval;

    private boolean urgent;
    public FormDto() {
        super();
        this.id = UUID.randomUUID();
    }

    public FormDto(Form form) {
        this();
        this.id = form.getId();
        this.username = form.getUsername();
        this.firstName = form.getFirstName();
        this.lastName = form.getLastName();
        this.email = form.getEmail();
        this.eventType = form.getEventType();
        this.gradeFormat = form.getGradeFormat();
        this.description = form.getDescription();
        this.eventDate = form.getEventDate();
        this.eventTime = form.getEventTime();
        this.location = form.getLocation();
        this.justification = form.getJustification();
        this.cost = form.getCost();
        this.reimbursement = form.getReimbursement();
        this.daysMissed = form.getDaysMissed();
        this.attachment = form.getAttachment();
        this.supervisorApproval = form.getSupervisorApproval();
        this.departmentHeadApproval = form.getDepartmentHeadApproval();
        this.urgent = isUrgent();
    }

    @JsonIgnore
    public Form getForm() {
        return new Form(this.id, this.username, this.firstName, this.lastName, this.email, this.eventType, this.gradeFormat,
                this.description, this.eventDate, this.eventTime, this.location, this.justification, this.cost, this.reimbursement,
                this.daysMissed, this.attachment, this.supervisorApproval, this.departmentHeadApproval, this.urgent);
    }

    public boolean isUrgent() {
        return LocalDate.now().plusWeeks(2).isAfter(this.eventDate);
    }
}

