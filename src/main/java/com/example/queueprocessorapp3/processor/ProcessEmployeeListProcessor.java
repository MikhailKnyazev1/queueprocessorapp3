package com.example.queueprocessorapp3.processor;

import com.example.queueprocessorapp3.entity.Employee;
import com.example.queueprocessorapp3.entity.EmployeeMessage;
import com.example.queueprocessorapp3.entity.Message;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ProcessEmployeeListProcessor implements Processor {

    private static final Logger logger = LoggerFactory.getLogger(ProcessEmployeeListProcessor.class);
    private final RestTemplate restTemplate;
    private final String bulkServiceUrl = "http://localhost:8080/employees/bulk";
    private final String singleServiceUrl = "http://localhost:8080/employees";
    private final ObjectMapper objectMapper;

    @Autowired
    public ProcessEmployeeListProcessor(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        String json = exchange.getIn().getBody(String.class);
        String messageType = exchange.getIn().getHeader("type", String.class);

        if ("addEmployeeList".equals(messageType)) {
            processEmployeeList(json);
        } else if ("addEmployee".equals(messageType)) {
            processSingleEmployee(json);
        } else {
            logger.error("Unknown message type: " + messageType);
        }
    }

    private void processEmployeeList(String json) throws Exception {
        EmployeeMessage employeeMessage = objectMapper.readValue(json, EmployeeMessage.class);

        // Преобразование списка Employee в список Message
        List<Message> messages = employeeMessage.getStaff().stream()
                .map(this::convertToMessage)
                .collect(Collectors.toList());

        ResponseEntity<String> response = restTemplate.postForEntity(
                bulkServiceUrl,
                messages,
                String.class
        );

        logResponse(response);
    }

    private void processSingleEmployee(String json) throws Exception {
        Employee employee = objectMapper.readValue(json, Employee.class);

        ResponseEntity<String> response = restTemplate.postForEntity(
                singleServiceUrl,
                employee,
                String.class
        );

        logResponse(response);
    }

    private Message convertToMessage(Employee employee) {
        Message message = new Message();
        // Заполнение полей message из employee
        message.setFirstName(employee.getFirstName());
        message.setLastName(employee.getLastName());
        message.setAge(employee.getAge());
        message.setProfession(employee.getProfession());
        return message;
    }

    private void logResponse(ResponseEntity<String> response) {
        if (response.getStatusCode() != HttpStatus.OK) {
            logger.error("Failed to send employee data to REST service. Status code: " + response.getStatusCode());
        }
    }
}
