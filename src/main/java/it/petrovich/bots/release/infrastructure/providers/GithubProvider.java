package it.petrovich.bots.release.infrastructure.providers;

import it.petrovich.bots.release.infrastructure.model.ReleaseInfoEntity;
import it.petrovich.bots.release.infrastructure.model.SourceType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.net.URL;
import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
public class GithubProvider implements Provider {

    private final SourceType sourceType;

    @Override
    public SourceType getType() {
        return sourceType;
    }

    @Override
    @SneakyThrows
    public Collection<ReleaseInfoEntity> retrieve(String url) {
        final var requestUrl = new URL(url);
        return Jsoup.connect(url)
                .get()
                .getElementsByAttributeValueContaining("href", "/tag/")
                .stream()
                .filter(element -> !"Notes".contains(element.text()))
                .map(element -> new ReleaseInfoEntity(sourceType, buildUrl(requestUrl, element), element.text()))
                .toList();
    }

    private String buildUrl(URL requestUrl, Element element) {
        return requestUrl.getProtocol() + "://" + requestUrl.getHost() + element.attributes().get("href");
    }
}
