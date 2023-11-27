package com.example.queueprocessorapp3;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageProcessor implements Processor {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        String json = exchange.getIn().getBody(String.class);
        logger.info("Received JSON: {}", json);

        // Логирование заголовков
        logger.info("Headers: {}", exchange.getIn().getHeaders());

        Message message = objectMapper.readValue(json, Message.class);

        // Проверка заголовка 'type'
        String typeHeader = (String)exchange.getIn().getHeader("type", String.class);
        logger.info("Type Header: {}", typeHeader);


        if ("employee".equals(typeHeader)) {
            message.setHandledTimestamp(Instant.now().toEpochMilli());
            message.setStatus("Complete");
            logger.info("Processed employee message: {}", message);
        } else {
            logger.info("Received a non-employee message or missing 'type' header");
        }

        String modifiedJson = objectMapper.writeValueAsString(message);
        exchange.getIn().setBody(modifiedJson);
    }
}
