package it.petrovich.bots.release.domain;

import it.petrovich.bots.notification.infrastructure.NotificationProvider;
import it.petrovich.bots.notification.infrastructure.ReleaseNotification;
import it.petrovich.bots.release.infrastructure.ProviderNotFoundException;
import it.petrovich.bots.release.infrastructure.Repository;
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
    private final Repository repository;


    @Scheduled(cron = "${release.cron}")
    public void updateRelease() {
        final var configs = repository.getConfigs();

        for (SourceConfigEntity config : configs) {
            final var provider = providers.get(config.getType());
            if (provider == null) {
                throw new ProviderNotFoundException(config.getType());
            }
            final var releases = repository.getReleases(config.getId());
            final var releaseInfo = provider.retrieve(config.getUrl());
            for (ReleaseInfoEntity newRelease : releaseInfo) {
                if (!releases.contains(newRelease.getVersion())) {
                    newRelease.setConfigId(config.getId());
                    repository.save(newRelease);
                    log.debug("Saved new release: {} {} {}", newRelease.getType(), newRelease.getVersion(),
                            newRelease.getReleaseUrl());
                    notificationProvider.push(new ReleaseNotification(config.getLibraryName(), newRelease.getType(),
                            newRelease.getVersion(), newRelease.getReleaseUrl()));
                }
            }
        }
    }
}
