package com.example.queueprocessorapp3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;

@Component
public class MessageRoute extends RouteBuilder {
    private static final Logger logger = LoggerFactory.getLogger(MessageRoute.class);
    private final ObjectMapper objectMapper;

    public MessageRoute() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // Регистрация JavaTimeModule
    }

    @Override
    public void configure() throws Exception {
        logger.info("Configuring Camel routes");

        from("spring-rabbitmq:default?queues=test.input")
                .bean(MessageProcessor.class) // Использование MessageProcessor для преобразования сообщения
                .choice()
                .when(header("type").isEqualTo("addEmployee"))
                .bean("employeeService", "addOrUpdateEmployee")
                .process(exchange -> {
                    Long employeeId = exchange.getIn().getBody(Long.class);
                    String json = objectMapper.writeValueAsString(Collections.singletonMap("id", employeeId));
                    exchange.getIn().setBody(json);
                })
                .toD("spring-rabbitmq:responseQueue?routingKey=responseQueue")
                .when(header("type").isEqualTo("getEmployee"))
                .bean("employeeService", "getEmployeeById")
                .process(exchange -> {
                    Employee employee = exchange.getIn().getBody(Employee.class);
                    String json;
                    if (employee != null) {
                        json = objectMapper.writeValueAsString(employee);
                        exchange.getIn().setHeader("status", "OK");
                    } else {
                        json = "";
                        exchange.getIn().setHeader("status", "ERROR");
                        exchange.getIn().setHeader("errorMessage", "Employee not found");
                    }
                    exchange.getIn().setBody(json);
                })
                .toD("spring-rabbitmq:responseQueue?routingKey=responseQueue")
                .end();

        logger.info("Camel routes configuration complete");
    }
}
