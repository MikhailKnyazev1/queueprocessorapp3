package com.example.queueprocessorapp3.controller;

import com.example.queueprocessorapp3.entity.Employee;
import com.example.queueprocessorapp3.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.addEmployee(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new EntityNotFoundException("Сотрудник с ID " + id + " не найден"));
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Employee>> addEmployees(@Valid @RequestBody List<Employee> employees) {
        List<Employee> savedEmployees = employees.stream()
                .map(employeeService::addEmployee)
                .collect(Collectors.toList());
        return ResponseEntity.ok(savedEmployees);
    }
}
