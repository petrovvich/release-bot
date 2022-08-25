package it.petrovich.bots.telegram.infrastructure;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramProviderImpl implements TelegramProvider {
    private final Collection<NotificationBot> bots;

    @Override
    public void send(NotificationEvent event) {
        bots.stream()
                .filter(bot -> bot.getChannel().equalsIgnoreCase(event.channel()))
                .peek(bot -> bot.sendMessage(event.message()))
                .toList();
    }
}
