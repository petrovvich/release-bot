package it.petrovich.bots.release.infrastructure;

import it.petrovich.bots.release.infrastructure.model.NotificationState;
import it.petrovich.bots.release.infrastructure.model.ReleaseInfoEntity;
import it.petrovich.bots.release.infrastructure.model.SourceConfigEntity;
import it.petrovich.bots.release.infrastructure.repo.ReleaseInfoSpringJpaRepo;
import it.petrovich.bots.release.infrastructure.repo.SourceConfigSpringJpaRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReleaseRepositoryImpl implements ReleaseRepository {
    private final ReleaseInfoSpringJpaRepo releaseInfoRepo;
    private final SourceConfigSpringJpaRepo sourceConfigRepo;

    @Override
    public Collection<SourceConfigEntity> getConfigs() {
        return sourceConfigRepo.findAll();
    }

    @Override
    public Collection<String> getReleases(UUID configId, NotificationState state) {
        return releaseInfoRepo.findReleases(configId, state);
    }

    @Override
    public void save(ReleaseInfoEntity newRelease) {
        releaseInfoRepo.save(newRelease);
    }

    @Override
    public void update(UUID releaseId, NotificationState state) {
        final var found = releaseInfoRepo.findById(releaseId);
        if (found.isPresent()) {
            found.get().setState(state);
            releaseInfoRepo.save(found.get());
        }
    }
}
