package it.petrovich.bots.telegram.infrastructure;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Property;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@Factory
public class NotificationConfiguration {

    @Property(name = "notification.telegram")
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
