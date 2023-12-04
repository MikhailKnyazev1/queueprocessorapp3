package com.example.queueprocessorapp3.processor;

import com.example.queueprocessorapp3.service.EmployeeService;
import com.example.queueprocessorapp3.entity.Employee;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.time.Instant;

@Component
public class AddEmployeeProcessor implements Processor {

    private final EmployeeService employeeService;
    private final ObjectMapper objectMapper;

    @Autowired
    public AddEmployeeProcessor(EmployeeService employeeService, ObjectMapper objectMapper) {
        this.employeeService = employeeService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        String json = exchange.getIn().getBody(String.class);
        JsonNode jsonNode = objectMapper.readTree(json);

        Employee employee = new Employee();
        // Предполагается, что у класса Employee есть соответствующие сеттеры
        employee.setFirstName(jsonNode.get("firstName").asText());
        employee.setLastName(jsonNode.get("lastName").asText());
        employee.setAge(jsonNode.get("age").asInt());
        employee.setProfession(jsonNode.get("profession").asText());
        employee.setHandledTimestamp(Instant.now());
        employee.setStatus("Complete");

        // Добавление или обновление сотрудника в базе данных
        Long employeeId = employeeService.addOrUpdateEmployee(employee);

        // Отправка ответного сообщения с ID сотрудника
        String responseJson = objectMapper.writeValueAsString(employeeId);
        exchange.getIn().setBody(responseJson);
    }
}
