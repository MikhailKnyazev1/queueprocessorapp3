package com.example.queueprocessorapp3.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.queueprocessorapp3.entity.EmployeeMessage;

@Component
public class ParseEmployeeMessageProcessor implements Processor {

    private final ObjectMapper objectMapper;

    public ParseEmployeeMessageProcessor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        String json = exchange.getIn().getBody(String.class);
        EmployeeMessage employeeMessage = objectMapper.readValue(json, EmployeeMessage.class);

        exchange.getIn().setBody(employeeMessage);
    }
}

