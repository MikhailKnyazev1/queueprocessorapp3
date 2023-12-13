package com.example.queueprocessorapp3.route;

import com.example.queueprocessorapp3.processor.AddEmployeeProcessor;
import com.example.queueprocessorapp3.processor.GetEmployeeProcessor;
import com.example.queueprocessorapp3.processor.ProcessEmployeeListProcessor;
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
                .when(header("type").isEqualTo("addEmployee"))
                .bean(AddEmployeeProcessor.class)
                .to("spring-rabbitmq:responseQueue?routingKey=responseQueue")
                .when(header("type").isEqualTo("getEmployee"))
                .bean(GetEmployeeProcessor.class)
                .to("spring-rabbitmq:responseQueue?routingKey=responseQueue")
                .when(header("type").isEqualTo("processEmployeeList"))
                .bean(ProcessEmployeeListProcessor.class)
                .endChoice()
                .end();

        logger.info("Camel routes configuration complete");
    }
}
