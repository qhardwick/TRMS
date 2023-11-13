package com.infy.services;

import com.infy.dtos.EmployeeDto;
import com.infy.dtos.FormDto;
import com.infy.entities.Employee;
import com.infy.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

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
        return employeeToBeUpdatedMono.flatMap(employee -> {
            if(updatedEmployeeDto.getFirstName() != null) {
                employee.setFirstName(updatedEmployeeDto.getFirstName());
            }
            if(updatedEmployeeDto.getLastName() != null) {
                employee.setLastName(updatedEmployeeDto.getLastName());
            }
            if(updatedEmployeeDto.getEmail() != null) {
                employee.setEmail(updatedEmployeeDto.getEmail());
            }
            if(updatedEmployeeDto.getSupervisor() != null) {
                employee.setSupervisor(updatedEmployeeDto.getSupervisor());
            }
            if(updatedEmployeeDto.getDepartment() != null) {
                employee.setDepartment(updatedEmployeeDto.getDepartment());
            }
            if(updatedEmployeeDto.getBenefitsCoordinator() != null) {
                employee.setBenefitsCoordinator(updatedEmployeeDto.getBenefitsCoordinator());
            }
            // Allowance, pending, and awarded are primitives and will need to be updated elsewhere to allow for 0.
            if(updatedEmployeeDto.getAllowance() != 0) {
                employee.setAllowance(updatedEmployeeDto.getAllowance());
            }
            if(updatedEmployeeDto.getPending() != 0) {
                employee.setPending(updatedEmployeeDto.getPending());
            }
            if(updatedEmployeeDto.getAwarded() != 0) {
                employee.setAwarded(updatedEmployeeDto.getAwarded());
            }
            if(updatedEmployeeDto.getInbox() != null) {
                employee.setInbox(updatedEmployeeDto.getInbox());
            }
            if(updatedEmployeeDto.getRequests() != null) {
                employee.setRequests(updatedEmployeeDto.getRequests());
            }
            if(updatedEmployeeDto.getEmployeeType() != null) {
                employee.setEmployeeType(updatedEmployeeDto.getEmployeeType());
            }

            return employeeRepository.save(employee);
        }).map(EmployeeDto::new);
    }

    // Delete Employee:
    @Override
    public Mono<Void> deleteEmployee(String username) {
        return employeeRepository.deleteById(username);
    }

    // Login:
    // Exactly the same as the findByUsername() method, but we're keeping it separate for now in case we want to add
    // additional functionality later.
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

        // Get Employee from database:
        Mono<Employee> requestingEmployeeMono = employeeRepository.findById(username);

        // Add employee info to the form:
        Mono<FormDto> updatedFormMono = addEmployeeInfoToForm(requestingEmployeeMono, requestForm);

        // Save the request form to the database:
        WebClient webClient = WebClient.create();
        Mono<FormDto> submittedForm = webClient
                .post()
                .uri(formsUrl)
                .body(Mono.just(requestForm), FormDto.class)
                .retrieve()
                .bodyToMono(FormDto.class);

        // Check if the form contains a direct supervisor pre-approval attachment:
        if(requestForm.getSupervisorApproval() != null) {
            //approveRequest()
            return submittedForm;
        }

        return submittedForm;
    }

    // Approve Request:
    @Override
    public Mono<Void> approveRequest(String username, UUID requestId) {

        // Get Approving Employee from database:
        Mono<Employee> approverMono = employeeRepository.findById(username);

        // Get Form from database:
        WebClient webClient = WebClient.create();
        Mono<FormDto> approvedFormMono = webClient
                .get()
                .uri(formsUrl + requestId)
                .retrieve()
                .bodyToMono(FormDto.class);

        // Add Form to Approver's Inbox:
        Mono<Void> updatedApproverMono = addToInbox(username, requestId);

        // Remove Form from Approver's Inbox:
        //Mono<Void> updatedFormMono = removeFromInbox(username, requestId);

        // Save the updated form to the database:
        //Mono<FormDto> updatedFormDtoMono = updatedFormMono.thenReturn(approvedFormMono.block());

        // Check if the form contains a direct supervisor pre-approval attachment:
        if(approvedFormMono.block().getSupervisorApproval() != null) {

        }
        return Mono.empty();
    }

    // Add Form to Approver's Inbox:
    private Mono<Void> addToInbox(String username, UUID formId) {
        Mono<Employee> approverMono = employeeRepository.findById(username);
        return approverMono.map(approver -> {
            approver.getInbox().add(formId);
            return approver;
        }).flatMap(employeeRepository::save).then();
    }

    // Utility method to add employee info to a form:
    private Mono<FormDto> addEmployeeInfoToForm(Mono<Employee> employeeMono, FormDto requestForm) {
        return employeeMono.map(employee -> {
            requestForm.setUsername(employee.getUsername());
            requestForm.setFirstName(employee.getFirstName());
            requestForm.setLastName(employee.getLastName());
            requestForm.setEmail(employee.getEmail());

            // Calculate the amount to be reimbursed. Will likely need to implement rounding or change to BigDecimal.
            // At the moment, we're not adjusting for the employee's allowance until Benco approval. May change later.
            requestForm.setReimbursement(requestForm.getCost() * requestForm.getEventType().getRate());

            employee.getRequests().add(requestForm.getId());
            employeeRepository.save(employee).subscribe();

            return requestForm;
        });
    }

}
