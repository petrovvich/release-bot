package it.petrovich.bots.notification.domain;

import it.petrovich.bots.notification.infrastructure.NotificationProvider;
import it.petrovich.bots.notification.infrastructure.ReleaseNotification;
import it.petrovich.bots.release.infrastructure.ReleaseRepository;
import it.petrovich.bots.release.infrastructure.model.NotificationState;
import it.petrovich.bots.telegram.infrastructure.NotificationEvent;
import it.petrovich.bots.telegram.infrastructure.TelegramProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private static final String MSG_TEMPLATE = """
            <b>New {0} version is available on {1}</b>
                        
            New release is under the version {2}!
                        
            <b><a href="{3}">Download and use!</a></b>
            """;
    private final NotificationProvider notificationProvider;
    private final TelegramProvider telegramProvider;
    private final ReleaseRepository releaseRepository;

    @Scheduled(cron = "${notification.cron}")
    public void sendNotification() {
        notificationProvider.pull()
                .ifPresent(releaseNotification -> {
                    sendToBot(releaseNotification);
                    releaseRepository.update(releaseNotification.releaseId(), NotificationState.SENT);
                });
    }

    private void sendToBot(ReleaseNotification releaseNotification) {
        telegramProvider.send(new NotificationEvent("releases",
                MessageFormat.format(MSG_TEMPLATE, releaseNotification.libraryName(),
                        releaseNotification.sourceType().name().toLowerCase(),
                        releaseNotification.version(), releaseNotification.releaseLink())));
    }

}
