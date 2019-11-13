package ch.puzzle.mq.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/jms")
public class JmsController {

    private final JmsService jmsService;

    public JmsController(JmsService jmsService) {
        this.jmsService = jmsService;
    }

    @PostMapping("/{queue}/{numberOfMessages}")
    public ResponseEntity sendMessage(@PathVariable String queue, @PathVariable Long numberOfMessages) {
        log.info("JmsController: Start sending {} messages to {} queue.", numberOfMessages, queue);
        jmsService.sendMessages(queue, numberOfMessages);
        return ResponseEntity.ok().build();
    }
}
