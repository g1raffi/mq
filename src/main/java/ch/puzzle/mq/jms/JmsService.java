package ch.puzzle.mq.jms;

import ch.puzzle.mq.config.ApplicationProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Service
public class JmsService {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;
    private final ApplicationProperties applicationProperties;

    private static Instant firstMessage;
    private static Instant lastMessage;
    private static int numberOfMessagesProcessed;

    public JmsService(JmsTemplate jmsTemplate, ObjectMapper objectMapper, ApplicationProperties applicationProperties) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
        this.applicationProperties = applicationProperties;
        firstMessage = null;
        lastMessage  = null;
        numberOfMessagesProcessed = 0;
    }

    public void resetCounter() {
        firstMessage = null;
        lastMessage = null;
        numberOfMessagesProcessed = 0;
        log.info("Reset counter.");
    }

    public String logStats() {
        String s = String.format("Processed %s messages in %s ms", numberOfMessagesProcessed, Duration.between(firstMessage, lastMessage).toMillis());
        log.info(s);
        return s;
    }

    @JmsListener(destination = "${application.jms.inboundQueue}", containerFactory = "jmsListenerContainerFactory")
    public void listener(String message) throws JsonProcessingException {
        if (firstMessage == null) {
            firstMessage = Instant.now();
        }
        lastMessage = Instant.now();
        numberOfMessagesProcessed++;
        Double value = objectMapper.readValue(message, Double.class);
        jmsTemplate.convertAndSend(applicationProperties.getJms().getOutboundQueue(), objectMapper.writeValueAsString(value * 10));
        log.info("Transformed message at {}, first message was at {}", lastMessage, firstMessage);
    }
}