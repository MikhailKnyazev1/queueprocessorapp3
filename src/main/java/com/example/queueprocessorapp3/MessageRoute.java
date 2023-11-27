package com.example.queueprocessorapp3;

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

        // Изменен URI для прослушивания очереди test.input
        from("spring-rabbitmq:default?queues=test.input")
                .process(new MessageProcessor())
                .to("spring-rabbitmq:test.output?routingKey=output");

        logger.info("Camel routes configuration complete");
    }
}
