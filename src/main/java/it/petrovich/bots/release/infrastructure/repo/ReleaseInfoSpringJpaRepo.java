package it.petrovich.bots.release.infrastructure.repo;

import it.petrovich.bots.release.infrastructure.model.ReleaseInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.UUID;

public interface ReleaseInfoSpringJpaRepo extends JpaRepository<ReleaseInfoEntity, UUID> {
    @Query(value = "select sub.* from ( " +
            "select version from release_info where config_id = :configId order by version desc limit 100) as sub " +
            "order by sub.version",
            nativeQuery = true)
    Collection<String> findReleases(@Param("configId") UUID configId);

    @Query(value = "select * from release_info where state = :state " +
            "and EXTRACT(MINUTE FROM (now() - creation_date)) > :olderThan order by version",
            nativeQuery = true)
    Collection<ReleaseInfoEntity> findReleases(@Param("state") String state,
                                               @Param("olderThan") Integer olderThan);
}
