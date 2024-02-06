package com.example.queueprocessorapp3.processor;

import com.example.queueprocessorapp3.client.EmployeeClient;
import com.example.queueprocessorapp3.entity.Employee;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ProcessSingleEmployeeProcessor implements Processor {

    @Autowired
    private EmployeeClient employeeClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void process(Exchange exchange) throws Exception {
        String json = exchange.getIn().getBody(String.class);
        Employee employee = objectMapper.readValue(json, Employee.class);

        // Отправляем данные через FeignClient
        Employee savedEmployee = employeeClient.addEmployee(employee);

        // Преобразование сохраненного сотрудника обратно в JSON и установка в тело обмена
        String responseJson = objectMapper.writeValueAsString(savedEmployee);
        exchange.getIn().setBody(responseJson);
    }
}
