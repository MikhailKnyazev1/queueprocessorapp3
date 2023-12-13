package com.example.queueprocessorapp3.service;

import com.example.queueprocessorapp3.repository.EmployeeRepository;
import com.example.queueprocessorapp3.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public Long addOrUpdateEmployee(Employee employee) {
        // Логика для добавления или обновления сотрудника
        Optional<Employee> existing = employeeRepository.findByFirstNameAndLastName(
                employee.getFirstName(), employee.getLastName()
        );

        Employee toSave;
        if (existing.isPresent()) {
            toSave = existing.get();
            toSave.setAge(employee.getAge());
            toSave.setProfession(employee.getProfession());
        } else {
            toSave = employee;
            toSave.setStatus("Active");
        }
        toSave.setHandledTimestamp(Instant.now());
        Employee saved = employeeRepository.save(toSave);
        return saved.getId();
    }

    // Измененный метод для получения сотрудника по ID
    public Optional<Employee> getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    // Метод для добавления нового сотрудника
    @Transactional
    public Employee addEmployee(Employee employee) {
        employee.setHandledTimestamp(Instant.now());
        employee.setStatus("Active");
        return employeeRepository.save(employee);
    }
}
