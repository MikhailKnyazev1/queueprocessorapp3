package com.example.queueprocessorapp3.service;

import com.example.queueprocessorapp3.entity.Message;
import com.example.queueprocessorapp3.repository.EmployeeRepository;
import com.example.queueprocessorapp3.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public Long addOrUpdateEmployee(Employee employee) {
        Optional<Employee> existingEmployee = employeeRepository.findByFirstNameAndLastName(
                employee.getFirstName(), employee.getLastName());

        Employee savedEmployee;
        if (existingEmployee.isPresent()) {
            // Обновление существующего сотрудника
            Employee existing = existingEmployee.get();
            existing.setAge(employee.getAge());
            existing.setProfession(employee.getProfession());
            existing.setHandledTimestamp(Instant.now());
            // Обновление других полей, если необходимо
            savedEmployee = employeeRepository.save(existing);
        } else {
            // Добавление нового сотрудника
            employee.setStatus("Active"); // Установка статуса для новых сотрудников
            employee.setHandledTimestamp(Instant.now());
            savedEmployee = employeeRepository.save(employee);
        }

        return savedEmployee.getId();
    }

    public Optional<Employee> getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Transactional
    public List<Message> addEmployees(List<Message> messages) {
        return messages.stream()
                .map(this::addMessage)
                .collect(Collectors.toList());
    }

    @Transactional
    public Message addMessage(Message message) {
        Employee employee = new Employee();
        employee.setFirstName(message.getFirstName());
        employee.setLastName(message.getLastName());
        employee.setAge(message.getAge());
        employee.setProfession(message.getProfession());
        employee.setHandledTimestamp(Instant.now());
        employee.setStatus("Active");

        Employee savedEmployee = employeeRepository.save(employee);

        // Обновление объекта Message новыми значениями
        message.setId(savedEmployee.getId()); // Добавление ID
        message.setHandledTimestamp(savedEmployee.getHandledTimestamp());
        message.setStatus(savedEmployee.getStatus());

        return message;
    }
}
