package com.infy.services;

import com.infy.constants.EmployeeType;
import com.infy.constants.Event;
import com.infy.constants.GradingFormat;
import com.infy.dtos.EmployeeDto;
import com.infy.dtos.FormDto;
import com.infy.entities.Employee;
import com.infy.repositories.EmployeeRepository;
import com.infy.services.EmployeeServiceImpl;
import com.infy.services.FormServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;


public class EmployeeServiceTest {

    private static EmployeeServiceImpl employeeService;

    private static EmployeeRepository employeeRepository;

    private static FormServiceImpl formService;

    private static final String formsUrl = "http://localhost:8080/trms/forms/";

    private static Employee standardEmployee;
    private static Employee updatedEmployee;
    private static FormDto standardFormDto;

    private static ArgumentCaptor<Employee> employeeCaptor;

    @BeforeEach
    public void init() {
        employeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);

        formService = mock(FormServiceImpl.class);
        employeeCaptor = ArgumentCaptor.forClass(Employee.class);

        setupStandardEmployee();
        setupUpdatedEmployee();
        setupStandardFormDto();
    }

    // Mock standard Employee database object:
    private static void setupStandardEmployee() {
        standardEmployee = new Employee();
        standardEmployee.setUsername("username");
        standardEmployee.setFirstName("firstName");
        standardEmployee.setLastName("lastName");
        standardEmployee.setEmail("email");
        standardEmployee.setSupervisor("supervisor");
        standardEmployee.setDepartment("department");
        standardEmployee.setBenefitsCoordinator("benefitsCoordinator");

        when(employeeRepository.save(standardEmployee)).thenReturn(Mono.just(standardEmployee));
        when(employeeRepository.findById(standardEmployee.getUsername())).thenReturn(Mono.just(standardEmployee));
    }

    // Mock updated Employee database object:
    private static void setupUpdatedEmployee() {
        updatedEmployee = new Employee();
        updatedEmployee.setUsername("username");
        updatedEmployee.setFirstName("updatedFirstName");
        updatedEmployee.setLastName("updatedLastName");
        updatedEmployee.setEmail("updatedEmail");
        updatedEmployee.setSupervisor("updatedSupervisor");
        updatedEmployee.setDepartment("updatedDepartment");
        updatedEmployee.setBenefitsCoordinator("updatedBenefitsCoordinator");

        updatedEmployee.setAllowance(900);
        updatedEmployee.setPending(50);
        updatedEmployee.setAwarded(50);
        updatedEmployee.getInbox().add(UUID.randomUUID());
        updatedEmployee.getRequests().add(UUID.randomUUID());
        updatedEmployee.setEmployeeType(EmployeeType.BENCO);

        when(employeeRepository.save(updatedEmployee)).thenReturn(Mono.just(updatedEmployee));
    }

    // Mock standard FormDto request object:
    private static void setupStandardFormDto() {
        standardFormDto = new FormDto();
        standardFormDto.setEventType(Event.UNIVERSITY_COURSE);
        standardFormDto.setGradeFormat(GradingFormat.LETTER_GRADE);
        standardFormDto.setDescription("description");
        standardFormDto.setEventDate(LocalDate.now().plusWeeks(3));
        standardFormDto.setEventTime(LocalTime.NOON);
        standardFormDto.setLocation("location");
        standardFormDto.setJustification("justification");
        standardFormDto.setCost(300);
    }

    // **********************************      Tests     **********************************

    // Test addEmployee() success:
    @Test
    public void testAddEmployeeSuccess() {

        // Call addEmployee() method:
        Mono<EmployeeDto> result = employeeService.addEmployee(new EmployeeDto(standardEmployee));

        // Verify that the result is the same as the standard Employee:
        StepVerifier.create(result)
                .expectNext(new EmployeeDto(standardEmployee))
                .verifyComplete();

    }

    // Test findByUsername() success:
    @Test
    public void testFindByUsernameSuccess() {

        // Call findByUsername() method:
        Mono<EmployeeDto> result = employeeService.findByUsername(standardEmployee.getUsername());

        // Verify that the employeeRepository.findById() method was called:
        verify(employeeRepository).findById(standardEmployee.getUsername());

        // Verify that the result is the same as the standard Employee:
        StepVerifier.create(result)
                .expectNext(new EmployeeDto(standardEmployee))
                .verifyComplete();

    }

    // Test findByUsername() returns empty:
    @Test
    public void testFindByUsernameEmpty() {

        // Call findByUsername() method with a username that does not exist:
        when(employeeRepository.findById("username")).thenReturn(Mono.empty());

        // Call findByUsername() method:
        Mono<EmployeeDto> result = employeeService.findByUsername("username");

        // Verify that the employeeRepository.findById() method was called:
        verify(employeeRepository).findById("username");

        // Verify that the result is empty:
        StepVerifier.create(result)
                .verifyComplete();
    }

    // Test updateEmployee() success:
    @Test
    public void testUpdateEmployeeSuccess() {

        // Call updateEmployee() method:
        Mono<EmployeeDto> result = employeeService.updateEmployee(standardEmployee.getUsername(), new EmployeeDto(updatedEmployee));

        // Verify that the result is the same as the updated Employee:
        StepVerifier.create(result)
                .expectNext(new EmployeeDto(updatedEmployee))
                .verifyComplete();
    }

    // Test updateEmployee() returns empty:
    @Test
    public void testUpdateEmployeeEmpty() {

        // Call updateEmployee() method with a username that does not exist:
        when(employeeRepository.findById("username")).thenReturn(Mono.empty());

        // Call updateEmployee() method:
        Mono<EmployeeDto> result = employeeService.updateEmployee("username", new EmployeeDto(updatedEmployee));

        // Verify that the employeeRepository.findById() method was called:
        verify(employeeRepository).findById("username");

        // Verify that the result is empty:
        StepVerifier.create(result)
                .verifyComplete();
    }

    // Test deleteEmployee() success:
    @Test
    public void testDeleteEmployeeSuccess() {

            // Call deleteEmployee() method:
            Mono<Void> result = employeeService.deleteEmployee(standardEmployee.getUsername());

            // Verify that the employeeRepository.deleteById() method was called:
            verify(employeeRepository).deleteById(standardEmployee.getUsername());

            // Verify that the result is empty:
            assertNull(result);
    }

    // Test login() success:
    @Test
    public void testLoginSuccess() {

        // Call login() method:
        Mono<EmployeeDto> result = employeeService.login(standardEmployee.getUsername());

        // Verify that the result is the same as the standard Employee:
        StepVerifier.create(result)
                .expectNext(new EmployeeDto(standardEmployee))
                .verifyComplete();
    }

    // Test login() returns empty:
    @Test
    public void testLoginEmpty() {

        // Call login() method with a username that does not exist:
        when(employeeRepository.findById("username")).thenReturn(Mono.empty());

        // Call login() method:
        Mono<EmployeeDto> result = employeeService.login("username");

        // Verify that the employeeRepository.findById() method was called:
        verify(employeeRepository).findById("username");

        // Verify that the result is empty:
        StepVerifier.create(result)
                .verifyComplete();
    }

    // Test submitRequest() success:
    @Test
    @Disabled
    public void testSubmitRequestSuccess() {

        // Call submitRequest() method:
        Mono<FormDto> result = employeeService.submitRequest(standardEmployee.getUsername(), standardFormDto);



    }
}
