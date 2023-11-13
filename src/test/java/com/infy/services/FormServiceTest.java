package com.infy.services;

import com.infy.constants.Event;
import com.infy.constants.GradingFormat;
import com.infy.dtos.FormDto;
import com.infy.entities.Form;
import com.infy.repositories.FormRepository;
import com.infy.services.FormServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class FormServiceTest {

    private static FormServiceImpl formService;
    private static FormRepository formRepository;
    private static Form requestForm;
    private static Form updatedForm;

    @BeforeEach
    public void init() {
        formRepository = mock(FormRepository.class);
        formService = new FormServiceImpl(formRepository);

        setupRequestForm();
        setupUpdatedForm();
    }

    // Mock standard Form database object:
    private void setupRequestForm() {
        requestForm = new Form();
        requestForm.setEventType(Event.UNIVERSITY_COURSE);
        requestForm.setGradeFormat(GradingFormat.LETTER_GRADE);
        requestForm.setDescription("description");
        requestForm.setEventDate(LocalDate.now().plusWeeks(3));
        requestForm.setEventTime(LocalTime.NOON);
        requestForm.setLocation("location");
        requestForm.setJustification("justification");
        requestForm.setCost(300);

        when(formRepository.save(requestForm)).thenReturn(Mono.just(requestForm));
        when(formRepository.findById(requestForm.getId())).thenReturn(Mono.just(requestForm));
    }

    // Mock updated Form database object:
    private void setupUpdatedForm() {
        updatedForm = new Form();
        updatedForm.setId(requestForm.getId());
        updatedForm.setEventType(Event.SEMINAR);
        updatedForm.setGradeFormat(GradingFormat.PRESENTATION);
        updatedForm.setDescription("updated description");
        updatedForm.setEventDate(LocalDate.now().plusWeeks(4));
        updatedForm.setEventTime(LocalTime.MIDNIGHT);
        updatedForm.setLocation("updated location");
        updatedForm.setJustification("updated justification");
        updatedForm.setCost(400);
        updatedForm.setDaysMissed(1);
        updatedForm.setAttachment("attachment.pdf");
        updatedForm.setSupervisorApproval("supervisor approval");
        updatedForm.setDepartmentHeadApproval("department head approval");

        when(formRepository.save(updatedForm)).thenReturn(Mono.just(updatedForm));
    }

    // Test addForm() success:
    @Test
    public void testAddFormSuccess() {

        // Call addForm() method:
        Mono<FormDto> result = formService.addForm(new FormDto(requestForm));

        // Verify that the result is the same as the standard Form:
        StepVerifier.create(result)
                .expectNext(new FormDto(requestForm))
                .verifyComplete();

    }

    // Test addForm() with null values:

    // Test addForm() with invalid values:

    // Test getFormById() success:
    @Test
    public void testGetFormByIdSuccess() {

        // Call getFormById() method:
        Mono<FormDto> result = formService.getFormById(requestForm.getId());

        // Verify that the formRepository.findById() method was called:
        verify(formRepository).findById(requestForm.getId());

        // Verify that the result is the same as the standard Form:
        StepVerifier.create(result)
                .expectNext(new FormDto(requestForm))
                .verifyComplete();
    }

    // Test getFormById() returns empty:
    @Test
    public void testGetFormByIdReturnsEmpty() {

        // Call getFormById() method with an ID that does not exist:
        when(formRepository.findById(requestForm.getId())).thenReturn(Mono.empty());

        // Call getFormById() method:
        Mono<FormDto> result = formService.getFormById(requestForm.getId());

        // Verify that the formRepository.findById() method was called:
        verify(formRepository).findById(requestForm.getId());

        // Verify that the result is empty:
        StepVerifier.create(result)
                .verifyComplete();
    }

    // Test updateFormById() success:
    @Test
    public void testUpdateFormByIdSuccess() {

        // Call updateFormById() method:
        Mono<FormDto> result = formService.updateFormById(requestForm.getId(), new FormDto(updatedForm));

        // Verify that the formRepository.findById() method was called:
        verify(formRepository).findById(requestForm.getId());

        // Verify that the result is the same as the standard Form:
        StepVerifier.create(result)
                .expectNext(new FormDto(updatedForm))
                .verifyComplete();
    }

    // Test updateFormById() returns empty:
    @Test
    public void testUpdateFormByIdEmpty() {

        // Call updateFormById() method with an ID that does not exist:
        when(formRepository.findById(requestForm.getId())).thenReturn(Mono.empty());

        // Call updateFormById() method:
        Mono<FormDto> result = formService.updateFormById(requestForm.getId(), new FormDto(updatedForm));

        // Verify that the formRepository.findById() method was called:
        verify(formRepository).findById(requestForm.getId());

        // Verify that the result is empty:
        StepVerifier.create(result)
                .verifyComplete();
    }

    // Test updateFormById() with null values:

    // Test updateFormById() with invalid values:

    // Test deleteFormById() success:
    @Test
    public void testDeleteFormByIdSuccess() {

        // Call deleteFormById() method:
        Mono<Void> result = formService.deleteFormById(requestForm.getId());

        // Verify that the formRepository.deleteById() method was called:
        verify(formRepository).deleteById(requestForm.getId());

        // Verify that the result is empty:
        assertNull(result);
    }

}
