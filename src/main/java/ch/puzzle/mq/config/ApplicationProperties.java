package ch.puzzle.mq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@Configuration
public class ApplicationProperties {

    private JmsProperties jms;

    @Data
    public static class JmsProperties {
        private String brokerUrl;
        private String username;
        private String password;
        private String queue;
    }
}
