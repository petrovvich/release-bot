package it.petrovich.bots.telegram.infrastructure;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class TelegramProviderImpl implements TelegramProvider {
    private final Collection<NotificationBot> bots;

    @Override
    public void send(NotificationEvent event) {
        log.debug("Try to send notification {}", event);
        bots.stream()
                .filter(bot -> bot.getChannel().equalsIgnoreCase(event.channel()))
                .peek(bot -> bot.sendMessage(event.message()))
                .toList();
    }
}
