package ch.puzzle.mq.jms;

import ch.puzzle.mq.config.ApplicationProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
public class JmsService {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;
    private final ApplicationProperties applicationProperties;

    private static Instant firstMessage;

    public JmsService(JmsTemplate jmsTemplate, ObjectMapper objectMapper, ApplicationProperties applicationProperties) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
        this.applicationProperties = applicationProperties;
        firstMessage = null;
    }

    @JmsListener(destination = "${application.jms.inboundQueue}", containerFactory = "jmsListenerContainerFactory")
    public void listener(String message) throws JsonProcessingException {
        if (firstMessage == null) {
            firstMessage = Instant.now();
        }
        Double value = objectMapper.readValue(message, Double.class);
        jmsTemplate.convertAndSend(applicationProperties.getJms().getOutboundQueue(), objectMapper.writeValueAsString(value * 10));
        log.info("JmsService: Transformed message at {}, first message was at {}", Instant.now(), firstMessage);
    }
}
