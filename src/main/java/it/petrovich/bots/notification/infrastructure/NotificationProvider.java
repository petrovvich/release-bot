package it.petrovich.bots.notification.infrastructure;

import java.util.Optional;

public interface NotificationProvider {
    void push(ReleaseNotification newRelease);

    Optional<ReleaseNotification> pull();
}
