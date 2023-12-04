package com.example.queueprocessorapp3.processor;

import com.example.queueprocessorapp3.service.EmployeeService;
import com.example.queueprocessorapp3.entity.Employee;
import com.example.queueprocessorapp3.converter.EmployeeConverter;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

@Component
public class AddEmployeeProcessor implements Processor {

    private final EmployeeService employeeService;
    private final ObjectMapper objectMapper;
    private final EmployeeConverter employeeConverter;

    @Autowired
    public AddEmployeeProcessor(EmployeeService employeeService, ObjectMapper objectMapper, EmployeeConverter employeeConverter) {
        this.employeeService = employeeService;
        this.objectMapper = objectMapper;
        this.employeeConverter = employeeConverter;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        String json = exchange.getIn().getBody(String.class);
        JsonNode jsonNode = objectMapper.readTree(json);

        Employee employee = employeeConverter.convert(jsonNode);

        // Добавление или обновление сотрудника в базе данных
        Long employeeId = employeeService.addOrUpdateEmployee(employee);

        // Отправка ответного сообщения с ID сотрудника
        String responseJson = objectMapper.writeValueAsString(employeeId);
        exchange.getIn().setBody(responseJson);
    }
}
