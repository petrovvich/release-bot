package it.petrovich.bots.release.infrastructure;

import it.petrovich.bots.release.infrastructure.model.SourceType;

public class ProviderNotFoundException extends RuntimeException {
    public ProviderNotFoundException(SourceType sourceType) {
        super("Provider not found for type " + sourceType);
    }
}
