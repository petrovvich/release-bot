package it.petrovich.bots.release.infrastructure;

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
public class RepositoryImpl implements Repository {
    private final ReleaseInfoSpringJpaRepo releaseInfoRepo;
    private final SourceConfigSpringJpaRepo sourceConfigRepo;

    @Override
    public Collection<SourceConfigEntity> getConfigs() {
        return sourceConfigRepo.findAll();
    }

    @Override
    public Collection<String> getReleases(UUID configId) {
        return releaseInfoRepo.findReleases(configId);
    }

    @Override
    public void save(ReleaseInfoEntity newRelease) {
        releaseInfoRepo.save(newRelease);
    }
}
