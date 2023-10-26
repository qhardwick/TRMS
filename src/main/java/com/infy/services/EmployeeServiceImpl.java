package com.infy.services;

import com.infy.dto.EmployeeDto;
import com.infy.dto.FormDto;
import com.infy.entities.Employee;
import com.infy.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private static final String formsUrl = "http://localhost:8080/trms/forms/";

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Add new Employee:
    @Override
    public Mono<EmployeeDto> addEmployee(EmployeeDto newEmployeeDto) {
        return employeeRepository.save(newEmployeeDto.getEmployee())
                .map(EmployeeDto::new);
    }

    // View Employee:
    @Override
    public Mono<EmployeeDto> findByUsername(String username) {
        return employeeRepository.findById(username)
                .map(EmployeeDto::new);
    }

    // Update Employee:
    @Override
    public Mono<EmployeeDto> updateEmployee(String username, EmployeeDto updatedEmployeeDto) {

        Mono<Employee> employeeToBeUpdatedMono = employeeRepository.findById(username);
        return employeeToBeUpdatedMono.map(employee -> {
            if(updatedEmployeeDto.getFirstName() != null) {
                employee.setFirstName(updatedEmployeeDto.getFirstName());
            }
            if(updatedEmployeeDto.getLastName() != null) {
                employee.setLastName(updatedEmployeeDto.getLastName());
            }
            //if(updatedEmployee)

            return employee;
        }).map(EmployeeDto::new);
    }

    // Delete Employee:
    @Override
    public Mono<Void> deleteEmployee(String username) {
        return employeeRepository.deleteById(username);
    }

    // Login:
    @Override
    public Mono<EmployeeDto> login(String username) {
        return employeeRepository.findById(username)
                .map(EmployeeDto::new);
    }

    // Submit Request:
    // For now we're going to pull most of the basic employee details from the user's profile rather than making them
    // include it in the form. This may change later.
    @Override
    public Mono<FormDto> submitRequest(String username, FormDto requestForm) {
        Mono<Employee> requestingEmployee = employeeRepository.findById(username)
            .flatMap(employee -> {
                // Set the employee's username, first name, last name, and email in the request form:
                requestForm.setUsername(employee.getUsername());
                requestForm.setFirstName(employee.getFirstName());
                requestForm.setLastName(employee.getLastName());
                requestForm.setEmail(employee.getEmail());

                // Calculate the amount to be reimbursed, adjusting for the employee's allowance if necessary:
                // Will likely either need to add rounding or change to BigDecimal:
                double reimbursement = requestForm.getCost() * requestForm.getEventType().getRate();
                if(employee.getAwarded() + employee.getPending() + reimbursement > employee.getAllowance()) {
                    reimbursement = employee.getAllowance() - employee.getAwarded() - employee.getPending();
                }
                requestForm.setReimbursement(reimbursement);

                // Adjust the employee's pending reimbursement and add the request to their list of requests:
                employee.setPending(employee.getPending() + reimbursement);
                employee.getRequests().add(requestForm.getId());

                return employeeRepository.save(employee);
        });

        // Save the request form to the database:
        WebClient webClient = WebClient.create();
        Mono<FormDto> submittedForm = webClient
                .post()
                .uri(formsUrl)
                .body(Mono.just(requestForm), FormDto.class)
                .retrieve()
                .bodyToMono(FormDto.class);

        if(requestForm.getSupervisorApproval() != null) {
            // TODO: Call supervisor approval method
            return submittedForm;
        }

        return submittedForm;
    }
}
