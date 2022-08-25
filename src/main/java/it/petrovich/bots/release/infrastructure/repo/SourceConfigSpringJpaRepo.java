package it.petrovich.bots.release.infrastructure.repo;

import it.petrovich.bots.release.infrastructure.model.SourceConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SourceConfigSpringJpaRepo extends JpaRepository<SourceConfigEntity, UUID> {
}
