package it.petrovich.bots.release.infrastructure.repo;

import it.petrovich.bots.release.infrastructure.model.SourceConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.UUID;

public interface SourceConfigSpringJpaRepo extends JpaRepository<SourceConfigEntity, UUID> {

    @Query(value = "select * from release_config order by update_date limit :limit", nativeQuery = true)
    Collection<SourceConfigEntity> findAllWithLimit(@Param("limit") Integer limit);
}
