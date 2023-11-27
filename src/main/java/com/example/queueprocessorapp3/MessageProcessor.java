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

        Message message = objectMapper.readValue(json, Message.class);

        // Добавляем поля, если тип сообщения - "employee"
        if ("employee".equals(exchange.getIn().getHeader("type"))) {
            message.setHandledTimestamp(Instant.now().toEpochMilli());
            message.setStatus("Complete");
            logger.info("Processed employee message: {}", message);
        } else {
            // Здесь можете добавить дополнительную логику обработки
            logger.info("Received a non-employee message");
        }

        String modifiedJson = objectMapper.writeValueAsString(message);
        exchange.getIn().setBody(modifiedJson);
    }
}
