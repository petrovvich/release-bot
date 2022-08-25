package it.petrovich.bots.release.infrastructure.providers;

import it.petrovich.bots.release.infrastructure.model.ReleaseInfoEntity;
import it.petrovich.bots.release.infrastructure.model.SourceType;

import java.util.Collection;

public interface Provider {
    SourceType getType();

    Collection<ReleaseInfoEntity> retrieve(String url);
}
