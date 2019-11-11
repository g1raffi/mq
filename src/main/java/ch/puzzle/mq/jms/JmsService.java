package ch.puzzle.mq.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JmsService {

    @JmsListener(destination = "${application.jms.queue}", containerFactory = "jmsListenerContainerFactory")
    public void listen(String message) {
        log.info("JMS: Listener received message: {}", message);
    }
}
