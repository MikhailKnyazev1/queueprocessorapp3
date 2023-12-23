package com.example.queueprocessorapp3.route;

import com.example.queueprocessorapp3.processor.ProcessEmployeeListProcessor;
import com.example.queueprocessorapp3.processor.AddEmployeeProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class MessageRoute extends RouteBuilder {
    private static final Logger logger = LoggerFactory.getLogger(MessageRoute.class);

    @Override
    public void configure() throws Exception {
        logger.info("Configuring Camel routes");

        from("spring-rabbitmq:default?queues=test.input")
                .process(exchange -> {
                    String messageType = exchange.getIn().getHeader("type", String.class);
                    logger.info("Received message with type: " + messageType);
                })
                .choice()
                .when(header("type").isEqualTo("addEmployeeList"))
                .bean(ProcessEmployeeListProcessor.class)
                .when(header("type").isEqualTo("addEmployee"))
                .bean(AddEmployeeProcessor.class)
                .endChoice()
                .end();

        logger.info("Camel routes configuration complete");
    }
}
