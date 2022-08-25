package it.petrovich.bots.release.infrastructure;

import it.petrovich.bots.release.infrastructure.model.NotificationState;
import it.petrovich.bots.release.infrastructure.model.ReleaseInfoEntity;
import it.petrovich.bots.release.infrastructure.model.SourceConfigEntity;

import java.util.Collection;
import java.util.UUID;

public interface ReleaseRepository {
    Collection<SourceConfigEntity> getConfigs();

    SourceConfigEntity getConfig(UUID id);

    Collection<String> getReleases(UUID configId);

    Collection<ReleaseInfoEntity> getReleases(NotificationState notificationState);

    void save(ReleaseInfoEntity newRelease);

    void update(UUID releaseId, NotificationState prepared);
}
