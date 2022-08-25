package it.petrovich.bots.telegram.infrastructure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "notification")
public class NotificationConfiguration {

    private Map<String, BotProperties> telegram;

    @Bean
    public Collection<NotificationBot> bots() {
        return telegram.entrySet()
                .stream()
                .map(entry -> new NotificationBot(entry.getValue().getToken(), entry.getValue().getUsername(),
                        entry.getValue().getChats(), entry.getKey()))
                .toList();
    }

}
