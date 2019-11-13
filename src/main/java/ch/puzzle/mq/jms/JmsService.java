package ch.puzzle.mq.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.stream.LongStream;

@Slf4j
@Service
public class JmsService {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public JmsService(JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }

    @Async
    public void sendMessages(String queue, Long numberOfMessages) {
        LongStream.range(0L, numberOfMessages).forEach(l -> {
            try {
                jmsTemplate.convertAndSend(queue, objectMapper.writeValueAsString(Math.random()));
            } catch (JsonProcessingException ignored) { }
        });
    }
}
