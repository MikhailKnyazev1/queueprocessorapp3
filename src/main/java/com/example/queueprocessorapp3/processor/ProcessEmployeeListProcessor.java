package com.example.queueprocessorapp3.processor;

import com.example.queueprocessorapp3.entity.Employee;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Component
public class ProcessEmployeeListProcessor implements Processor {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final String serviceUrl = "http://localhost:8080/employees";

    @Autowired
    public ProcessEmployeeListProcessor(ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        String json = exchange.getIn().getBody(String.class);
        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode staffNode = rootNode.path("staff");

        if (!staffNode.isMissingNode() && staffNode.isArray()) {
            List<Employee> employees = objectMapper.convertValue(staffNode, new TypeReference<List<Employee>>(){});

            for (Employee employee : employees) {
                restTemplate.postForObject(serviceUrl, employee, String.class); // Отправка каждого сотрудника в REST-сервис
            }
        } else {
            String errorMessage = "The 'staff' field is missing or is not an array in the input message.";
            exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 400); // Установка кода ошибки HTTP
            exchange.getIn().setBody(errorMessage);
            LoggerFactory.getLogger(ProcessEmployeeListProcessor.class).error(errorMessage);
        }
    }
}
