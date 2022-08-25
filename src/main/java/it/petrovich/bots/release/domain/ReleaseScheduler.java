package it.petrovich.bots.release.domain;

import it.petrovich.bots.notification.infrastructure.NotificationProvider;
import it.petrovich.bots.notification.infrastructure.ReleaseNotification;
import it.petrovich.bots.release.infrastructure.ProviderNotFoundException;
import it.petrovich.bots.release.infrastructure.ReleaseRepository;
import it.petrovich.bots.release.infrastructure.model.NotificationState;
import it.petrovich.bots.release.infrastructure.model.ReleaseInfoEntity;
import it.petrovich.bots.release.infrastructure.model.SourceConfigEntity;
import it.petrovich.bots.release.infrastructure.model.SourceType;
import it.petrovich.bots.release.infrastructure.providers.Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReleaseScheduler {
    private final NotificationProvider notificationProvider;
    private final Map<SourceType, Provider> providers;
    private final ReleaseRepository releaseRepository;


    @Scheduled(cron = "${release.cron}")
    public void updateRelease() {
        final var configs = releaseRepository.getConfigs();

        for (SourceConfigEntity config : configs) {
            final var provider = providers.get(config.getType());
            if (provider == null) {
                throw new ProviderNotFoundException(config.getType());
            }
            final var releases = releaseRepository.getReleases(config.getId(), NotificationState.NEW);
            final var releaseInfo = provider.retrieve(config.getUrl());
            for (ReleaseInfoEntity newRelease : releaseInfo) {
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
        }
    }
}
