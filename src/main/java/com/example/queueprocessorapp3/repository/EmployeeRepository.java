package com.example.queueprocessorapp3.repository;

import com.example.queueprocessorapp3.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByFirstNameAndLastName(String firstName, String lastName);
}
