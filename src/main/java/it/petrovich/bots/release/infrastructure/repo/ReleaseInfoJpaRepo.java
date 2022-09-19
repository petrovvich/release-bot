package it.petrovich.bots.release.infrastructure.repo;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import it.petrovich.bots.release.infrastructure.model.ReleaseInfoEntity;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface ReleaseInfoJpaRepo extends JpaRepository<ReleaseInfoEntity, UUID> {
    @Query(value = "select sub.* from ( " +
            "select version from release_info where config_id = :configId order by version desc limit 100) as sub " +
            "order by sub.version",
            nativeQuery = true)
    Collection<String> findReleases(UUID configId);

    @Query(value = "select * from release_info where state = :state " +
            "and EXTRACT(MINUTE FROM (now() - creation_date)) > :olderThan order by version",
            nativeQuery = true)
    Collection<ReleaseInfoEntity> findReleases(String state, Integer olderThan);
}
