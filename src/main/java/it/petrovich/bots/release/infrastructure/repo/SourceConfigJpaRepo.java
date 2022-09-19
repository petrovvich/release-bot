package it.petrovich.bots.release.infrastructure.repo;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.jpa.repository.JpaRepository;
import it.petrovich.bots.release.infrastructure.model.SourceConfigEntity;

import java.util.Collection;
import java.util.UUID;

public interface SourceConfigJpaRepo extends JpaRepository<SourceConfigEntity, UUID> {

    @Query(value = "select * from release_config order by update_date limit :limit", nativeQuery = true)
    Collection<SourceConfigEntity> findAllWithLimit(Integer limit);
}
