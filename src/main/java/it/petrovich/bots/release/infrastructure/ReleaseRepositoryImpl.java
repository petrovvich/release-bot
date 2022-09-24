package it.petrovich.bots.release.infrastructure;

import it.petrovich.bots.release.infrastructure.model.NotificationState;
import it.petrovich.bots.release.infrastructure.model.ReleaseInfoEntity;
import it.petrovich.bots.release.infrastructure.model.SourceConfigEntity;
import it.petrovich.bots.release.infrastructure.repo.ReleaseInfoJpaRepo;
import it.petrovich.bots.release.infrastructure.repo.SourceConfigJpaRepo;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.UUID;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class ReleaseRepositoryImpl implements ReleaseRepository {
    private final ReleaseInfoJpaRepo releaseInfoRepo;
    private final SourceConfigJpaRepo sourceConfigRepo;

    @Override
    public Collection<SourceConfigEntity> getConfigs() {
        return sourceConfigRepo.findAllWithLimit(5);
    }

    @Override
    public SourceConfigEntity getConfig(UUID id) {
        return sourceConfigRepo.findById(id).get();
    }

    @Override
    public Collection<String> getReleases(UUID configId) {
        return releaseInfoRepo.findReleases(configId);
    }

    @Override
    public Collection<ReleaseInfoEntity> getReleases(NotificationState notificationState) {
        return releaseInfoRepo.findReleases(notificationState.name(), 20);
    }

    @Override
    public void save(ReleaseInfoEntity newRelease) {
        releaseInfoRepo.saveAndFlush(newRelease);
    }

    @Override
    public void update(UUID releaseId, NotificationState state) {
        final var found = releaseInfoRepo.findById(releaseId);
        if (found.isPresent()) {
            log.debug("Try to update entity {}", found);
            found.get().setState(state);
            releaseInfoRepo.saveAndFlush(found.get());
        }
    }

    @Override
    public void update(SourceConfigEntity source) {
        sourceConfigRepo.save(source);
    }
}
