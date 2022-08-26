package it.petrovich.bots.release.infrastructure.repo;

import it.petrovich.bots.release.infrastructure.model.SourceConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.UUID;

public interface SourceConfigSpringJpaRepo extends JpaRepository<SourceConfigEntity, UUID> {

    Collection<SourceConfigEntity> findAllByUpdateDateBefore(OffsetDateTime olderThan);
}
