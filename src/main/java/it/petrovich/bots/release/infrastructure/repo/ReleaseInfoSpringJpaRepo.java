package it.petrovich.bots.release.infrastructure.repo;

import it.petrovich.bots.release.infrastructure.model.ReleaseInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.UUID;

public interface ReleaseInfoSpringJpaRepo extends JpaRepository<ReleaseInfoEntity, UUID> {
    @Query(value = "select version from ReleaseInfoEntity where configId = :configId")
    Collection<String> findReleases(@Param("configId") UUID configId);

    @Query(value = "select * from release_info where state = :state " +
            "and EXTRACT(MINUTE FROM (now() - creation_date)) > :olderThan", nativeQuery = true)
    Collection<ReleaseInfoEntity> findReleases(@Param("state") String state,
                                               @Param("olderThan") Integer olderThan);
}
