package it.petrovich.bots.telegram.infrastructure;

public interface TelegramProvider {

    void send(NotificationEvent event);
}
