package com.example.queueprocessorapp3;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; // Добавленный импорт для JavaTimeModule
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;

@Component
public class MessageProcessor implements Processor {

    private final ObjectMapper objectMapper;
    private final EmployeeService employeeService;
    private static final Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    @Autowired
    public MessageProcessor(EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // Регистрация модуля JavaTimeModule
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        byte[] messageBytes = exchange.getIn().getBody(byte[].class);
        String json = new String(messageBytes, StandardCharsets.UTF_8);
        logger.info("Received JSON: {}", json);

        String typeHeader = exchange.getIn().getHeader("type", String.class);

        if ("addEmployee".equals(typeHeader)) {
            Employee employee = objectMapper.readValue(json, Employee.class);
            exchange.getIn().setBody(employee);
        } else if ("getEmployee".equals(typeHeader)) {
            JsonNode jsonNode = objectMapper.readTree(json);
            if (jsonNode.has("id")) {
                Long employeeId = jsonNode.get("id").asLong();
                exchange.getIn().setBody(employeeId);
            } else {
                logger.error("Missing 'id' in getEmployee request");
            }
        } else {
            logger.info("Received an unrecognized message type or missing 'type' header");
        }
    }
}
