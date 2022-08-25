package it.petrovich.bots.release.infrastructure.repo;

import it.petrovich.bots.release.infrastructure.model.NotificationState;
import it.petrovich.bots.release.infrastructure.model.ReleaseInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.UUID;

public interface ReleaseInfoSpringJpaRepo extends JpaRepository<ReleaseInfoEntity, UUID> {
    @Query(value = "select version from ReleaseInfoEntity where configId = :configId and state = :state")
    Collection<String> findReleases(@Param("configId") UUID configId,
                                    @Param("state") NotificationState state);
}
