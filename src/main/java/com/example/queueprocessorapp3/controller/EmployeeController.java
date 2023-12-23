package com.example.queueprocessorapp3.controller;

import com.example.queueprocessorapp3.entity.Employee;
import com.example.queueprocessorapp3.entity.Message;
import com.example.queueprocessorapp3.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Message>> addEmployees(@Valid @RequestBody List<Message> messages) {
        List<Message> savedMessages = employeeService.addEmployees(messages);
        return ResponseEntity.ok(savedMessages);
    }

    @PostMapping
    public ResponseEntity<Long> addEmployee(@Valid @RequestBody Employee employee) {
        Long employeeId = employeeService.addOrUpdateEmployee(employee);
        return ResponseEntity.ok(employeeId);
    }
}
