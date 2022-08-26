package it.petrovich.bots.notification.infrastructure;

import it.petrovich.bots.release.infrastructure.ReleaseRepository;
import it.petrovich.bots.release.infrastructure.model.NotificationState;
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
    private final ReleaseRepository releaseRepository;
    private static final Queue<ReleaseNotification> QUEUE = new ArrayBlockingQueue<>(200);

    @Override
    public void push(ReleaseNotification newRelease) {
        if (!QUEUE.contains(newRelease)) {
            QUEUE.add(newRelease);
            releaseRepository.update(newRelease.releaseId(), NotificationState.PREPARED);
        }
    }

    @Override
    public Optional<ReleaseNotification> pull() {
        return Optional.ofNullable(QUEUE.poll());
    }
}
