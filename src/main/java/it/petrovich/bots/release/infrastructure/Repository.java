package it.petrovich.bots.release.infrastructure;

import it.petrovich.bots.release.infrastructure.model.ReleaseInfoEntity;
import it.petrovich.bots.release.infrastructure.model.SourceConfigEntity;

import java.util.Collection;
import java.util.UUID;

public interface Repository {
    Collection<SourceConfigEntity> getConfigs();

    Collection<String> getReleases(UUID configId);

    void save(ReleaseInfoEntity newRelease);
}
