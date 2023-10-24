package com.infy.repositories;

import com.infy.entities.Employee;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends ReactiveCassandraRepository<Employee, String> {
}