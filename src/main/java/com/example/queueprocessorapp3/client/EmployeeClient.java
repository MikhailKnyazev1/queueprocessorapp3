package com.example.queueprocessorapp3.client;

import com.example.queueprocessorapp3.entity.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@FeignClient(name = "employeeClient", url = "http://localhost:8081")
public interface EmployeeClient {

    @PostMapping("/employees")
    Employee addEmployee(@RequestBody Employee employee);

    @GetMapping("/employees/{id}")
    Employee getEmployeeById(@PathVariable("id") Long id);

    @PostMapping("/employees/bulk")
    List<Employee> addEmployeesBulk(@RequestBody List<Employee> employees);
}
