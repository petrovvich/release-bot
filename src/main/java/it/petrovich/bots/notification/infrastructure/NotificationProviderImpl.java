package it.petrovich.bots.notification.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationProviderImpl implements NotificationProvider {

    private static final Queue<ReleaseNotification> QUEUE = new ArrayBlockingQueue<>(100);

    @Override
    public void push(ReleaseNotification newRelease) {
        QUEUE.add(newRelease);
    }

    @Override
    public Optional<ReleaseNotification> pull() {
        return Optional.ofNullable(QUEUE.poll());
    }
}
