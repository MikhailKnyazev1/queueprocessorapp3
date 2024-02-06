package com.example.queueprocessorapp3.service;

import com.example.queueprocessorapp3.entity.Message;
import com.example.queueprocessorapp3.repository.EmployeeRepository;
import com.example.queueprocessorapp3.entity.Employee;
import com.example.queueprocessorapp3.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            Employee existing = existingEmployee.get();
            existing.setAge(employee.getAge());
            existing.setProfession(employee.getProfession());
            existing.setCompany(employee.getCompany());
            existing.setEffectiveDate(employee.getEffectiveDate());
            savedEmployee = employeeRepository.save(existing);
        } else {
            savedEmployee = employeeRepository.save(employee);
        }

        return savedEmployee.getId();
    }

    public Optional<Employee> getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Transactional
    public List<Message> addEmployees(List<Message> messages) {
        List<Employee> employees = messages.stream()
                .map(EmployeeMapper.INSTANCE::messageToEmployee)
                .collect(Collectors.toList());
        employees.forEach(employeeRepository::save);
        return employees.stream()
                .map(EmployeeMapper.INSTANCE::employeeToMessage)
                .collect(Collectors.toList());
    }

    @Transactional
    public Message addMessage(Message message) {
        Employee employee = EmployeeMapper.INSTANCE.messageToEmployee(message);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.INSTANCE.employeeToMessage(savedEmployee);
    }
}
