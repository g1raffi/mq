package ch.puzzle.mq.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/jms")
public class JmsController {

    private final JmsService jmsService;

    public JmsController(JmsService jmsService) {
        this.jmsService = jmsService;
    }

    @PostMapping("/reset-counter")
    public ResponseEntity resetCounter() {
        jmsService.resetCounter();
        return ResponseEntity.ok().build();
    }
}
