package ch.puzzle.mq.jms;

import ch.puzzle.mq.config.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/jms")
public class JmsController {

    private final JmsTemplate jmsTemplate;
    private final ApplicationProperties applicationProperties;

    public JmsController(JmsTemplate jmsTemplate, ApplicationProperties applicationProperties) {
        this.jmsTemplate = jmsTemplate;
        this.applicationProperties = applicationProperties;
    }

    @PostMapping
    public ResponseEntity sendMessage(@RequestBody String message) {
        log.info("JMS: Sending message = {}", message);
        jmsTemplate.convertAndSend(
                applicationProperties.getJms().getQueue(), message
        );
        return ResponseEntity.ok(message);
    }
}
