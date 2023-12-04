package com.example.queueprocessorapp3.processor;

import com.example.queueprocessorapp3.service.EmployeeService;
import com.example.queueprocessorapp3.entity.Employee;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

@Component
public class GetEmployeeProcessor implements Processor {

    private final EmployeeService employeeService;
    private final ObjectMapper objectMapper;

    @Autowired
    public GetEmployeeProcessor(EmployeeService employeeService, ObjectMapper objectMapper) {
        this.employeeService = employeeService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        String json = exchange.getIn().getBody(String.class);
        JsonNode jsonNode = objectMapper.readTree(json);
        Long employeeId = jsonNode.get("id").asLong();

        Employee employee = employeeService.getEmployeeById(employeeId);

        if (employee != null) {
            // Сотрудник найден, отправляем данные обратно
            String responseJson = objectMapper.writeValueAsString(employee);
            exchange.getIn().setBody(responseJson);
            exchange.getIn().setHeader("status", "OK");
        } else {
            // Сотрудник не найден, отправляем сообщение об ошибке
            exchange.getIn().setHeader("status", "ERROR");
            exchange.getIn().setHeader("errorMessage", "Employee not found");
            exchange.getIn().setBody(null); // Или установите подходящее тело сообщения
        }
    }
}
