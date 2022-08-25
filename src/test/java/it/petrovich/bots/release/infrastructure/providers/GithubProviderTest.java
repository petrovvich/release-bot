package it.petrovich.bots.release.infrastructure.providers;

import it.petrovich.bots.release.infrastructure.model.SourceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GithubProviderTest {

    private final GithubProvider githubProvider = new GithubProvider(SourceType.GITHUB);

    @Test
    void testRetrieve() {
        final var links = githubProvider.retrieve("https://github.com/google/guava/tags");
        assertNotNull(links);
        assertEquals(10, links.size());
    }
}