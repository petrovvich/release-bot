package it.petrovich.bots.release.infrastructure;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import it.petrovich.bots.release.infrastructure.model.SourceType;
import it.petrovich.bots.release.infrastructure.providers.GithubProvider;
import it.petrovich.bots.release.infrastructure.providers.Provider;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Factory
public class ProvidersConfiguration {
    @Bean
    public Collection<Provider> providerCollection() {
        return List.of(new GithubProvider(SourceType.GITHUB));
    }

    @Bean
    public Map<SourceType, Provider> providers(Collection<Provider> providerCollection) {
        return providerCollection
                .stream()
                .collect(Collectors.toMap(Provider::getType, Function.identity()));
    }
}
