package com.infy.services;

import com.infy.dtos.DepartmentDto;
import com.infy.entities.Department;
import com.infy.repositories.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class DepartmentServiceTest {

    private static DepartmentServiceImpl departmentService;
    private static DepartmentRepository departmentRepository;
    private static Department standardDepartment;
    private static Department updatedDepartment;

    @BeforeEach
    public void init() {
        departmentRepository = mock(DepartmentRepository.class);
        departmentService = new DepartmentServiceImpl(departmentRepository);

        setupStandardDepartment();
        setupUpdatedDepartment();
    }

    // Mock standard Department database object:
    private void setupStandardDepartment() {
        standardDepartment = new Department();
        standardDepartment.setName("name");
        standardDepartment.setHead("head");

        when(departmentRepository.save(standardDepartment)).thenReturn(Mono.just(standardDepartment));
        when(departmentRepository.findById(standardDepartment.getName())).thenReturn(Mono.just(standardDepartment));
    }

    // Mock updated Department database object:
    private void setupUpdatedDepartment() {
        updatedDepartment = new Department();
        updatedDepartment.setName("updated name");
        updatedDepartment.setHead("updated head");

        when(departmentRepository.save(updatedDepartment)).thenReturn(Mono.just(updatedDepartment));
    }

    // Test addDepartment() success:
    @Test
    public void testAddDepartmentSuccess() {
        departmentService.addDepartment(new DepartmentDto(standardDepartment)).as(StepVerifier::create)
                .expectNextMatches(departmentDto -> {
                    assert departmentDto.getName().equals(standardDepartment.getName());
                    assert departmentDto.getHead().equals(standardDepartment.getHead());
                    return true;
                }).verifyComplete();
    }

    // Test addDepartment() with null values:

    // Test addDepartment() with invalid values:

    // Test findDepartmentByName() success:
    @Test
    public void testFindDepartmentByNameSuccess() {
        departmentService.findDepartmentByName(standardDepartment.getName()).as(StepVerifier::create)
                .expectNextMatches(departmentDto -> {
                    assert departmentDto.getName().equals(standardDepartment.getName());
                    assert departmentDto.getHead().equals(standardDepartment.getHead());
                    return true;
                }).verifyComplete();
    }

    // Test findDepartmentByName() returns empty:
    @Test
    public void testFindDepartmentByNameReturnsEmpty() {

        // Call findDepartmentByName() method with a name that does not exist:
        when(departmentRepository.findById("name")).thenReturn(Mono.empty());

        departmentService.findDepartmentByName("name").as(StepVerifier::create)
                .verifyComplete();
    }

    // Test updateDepartment() success:
    @Test
    public void testUpdateDepartmentSuccess() {
        departmentService.updateDepartment(standardDepartment.getName(), new DepartmentDto(updatedDepartment)).as(StepVerifier::create)
                .expectNextMatches(departmentDto -> {
                    assert departmentDto.getName().equals(updatedDepartment.getName());
                    assert departmentDto.getHead().equals(updatedDepartment.getHead());
                    return true;
                }).verifyComplete();
    }

    // Test updateDepartment() returns empty:
    @Test
    public void testUpdateDepartmentReturnsEmpty() {

        // Call updateDepartment() method with a name that does not exist:
        when(departmentRepository.findById("name")).thenReturn(Mono.empty());

        departmentService.updateDepartment("name", new DepartmentDto(updatedDepartment)).as(StepVerifier::create)
                .verifyComplete();
    }

    // Test updateDepartment() with null values:

    // Test updateDepartment() with invalid values:

    // Test deleteDepartment() success:
    @Test
    public void testDeleteDepartmentSuccess() {

        // Call deleteDepartment() method:
        Mono<Void> result = departmentService.deleteDepartment(standardDepartment.getName());

        // Verify that the departmentRepository.deleteById() method was called:
        verify(departmentRepository).deleteById(standardDepartment.getName());

        // Verify that the result is empty:
        assertNull(result);
    }
}
