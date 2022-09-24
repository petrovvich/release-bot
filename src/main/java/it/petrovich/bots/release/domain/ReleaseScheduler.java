package it.petrovich.bots.release.domain;

import io.micronaut.scheduling.annotation.Scheduled;
import it.petrovich.bots.notification.infrastructure.NotificationProvider;
import it.petrovich.bots.notification.infrastructure.ReleaseNotification;
import it.petrovich.bots.release.infrastructure.ProviderNotFoundException;
import it.petrovich.bots.release.infrastructure.ReleaseRepository;
import it.petrovich.bots.release.infrastructure.model.NotificationState;
import it.petrovich.bots.release.infrastructure.model.ReleaseInfoEntity;
import it.petrovich.bots.release.infrastructure.model.SourceConfigEntity;
import it.petrovich.bots.release.infrastructure.model.SourceType;
import it.petrovich.bots.release.infrastructure.providers.Provider;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Map;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class ReleaseScheduler {
    private final NotificationProvider notificationProvider;
    private final Map<SourceType, Provider> providers;
    private final ReleaseRepository releaseRepository;


    @Scheduled(cron = "${release.cron}")
    @Transactional(rollbackOn = Throwable.class, value = Transactional.TxType.REQUIRES_NEW)
    public void updateRelease() {
        log.debug("Begin update release info");
        var configs = releaseRepository.getConfigs();

        for (SourceConfigEntity config : configs) {
            final var provider = providers.get(config.getType());
            if (provider == null) {
                throw new ProviderNotFoundException(config.getType());
            }
            final var releases = releaseRepository.getReleases(config.getId());
            final var releaseInfo = provider.retrieve(config.getUrl());
            for (ReleaseInfoEntity newRelease : releaseInfo) {
                if (isFirstLaunch(releases)) {
                    newRelease.setConfigId(config.getId());
                    newRelease.setState(NotificationState.SENT);
                    releaseRepository.save(newRelease);
                    continue;
                }
                if (!releases.contains(newRelease.getVersion())) {
                    newRelease.setConfigId(config.getId());
                    newRelease.setState(NotificationState.NEW);
                    releaseRepository.save(newRelease);
                    log.debug("Saved new release: {} {} {}", newRelease.getType(), newRelease.getVersion(),
                            newRelease.getReleaseUrl());
                    notificationProvider.push(new ReleaseNotification(newRelease.getId(), config.getLibraryName(),
                            newRelease.getType(), newRelease.getVersion(), newRelease.getReleaseUrl()));
                }
            }
            config.setUpdateDate(OffsetDateTime.now());
            releaseRepository.update(config);
            log.debug("Finish proceed config {} {}", config.getId(), config.getLibraryName());
        }

        log.debug("End update release info");
    }

    private static boolean isFirstLaunch(Collection<String> releases) {
        return releases.isEmpty();
    }

    @Scheduled(cron = "${release.cron}")
    @Transactional(rollbackOn = Throwable.class, value = Transactional.TxType.REQUIRES_NEW)
    public void rescheduleNotification() {
        log.debug("Begin reschedule notifications");
        releaseRepository.getReleases(NotificationState.PREPARED)
                .forEach(release -> {
                    final var config = releaseRepository.getConfig(release.getConfigId());
                    notificationProvider.push(new ReleaseNotification(release.getId(), config.getLibraryName(),
                            release.getType(), release.getVersion(), release.getReleaseUrl()));
                });
        log.debug("End reschedule notifications");
    }
}
