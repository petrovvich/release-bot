package it.petrovich.bots.telegram.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collection;

@Getter
@AllArgsConstructor
public final class NotificationBot extends TelegramLongPollingBot {

    private final String botToken;
    private final String botUsername;
    private final Collection<String> chats;
    private final String channel;

    @Override
    public void onUpdateReceived(final Update update) {

    }

    @Override
    public void clearWebhook() {

    }

    @SneakyThrows
    public void sendMessage(final String messageBody) {
        for (String chatId : chats) {
            execute(SendMessage.builder()
                    .chatId(chatId)
                    .text(messageBody)
                    .parseMode(ParseMode.HTML)
                    .build());
        }
    }
}
