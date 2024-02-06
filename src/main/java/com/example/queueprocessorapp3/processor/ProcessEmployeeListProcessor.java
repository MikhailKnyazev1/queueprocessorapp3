package com.example.queueprocessorapp3.processor;

import com.example.queueprocessorapp3.client.EmployeeClient;
import com.example.queueprocessorapp3.entity.Employee;
import com.example.queueprocessorapp3.entity.EmployeeMessage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProcessEmployeeListProcessor implements Processor {

    @Autowired
    private EmployeeClient employeeClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void process(Exchange exchange) throws Exception {
        String json = exchange.getIn().getBody(String.class);
        EmployeeMessage employeeMessage = objectMapper.readValue(json, EmployeeMessage.class);

        // Устанавливаем компанию и дату для каждого сотрудника
        employeeMessage.getStaff().forEach(employee -> {
            employee.setCompany(employeeMessage.getCompany());
            employee.setEffectiveDate(employeeMessage.getEffectiveDate());
        });

        // Отправляем обновленные данные через FeignClient
        List<Employee> savedEmployees = employeeClient.addEmployeesBulk(employeeMessage.getStaff());
        String responseJson = objectMapper.writeValueAsString(savedEmployees);
        exchange.getIn().setBody(responseJson);
    }
}
