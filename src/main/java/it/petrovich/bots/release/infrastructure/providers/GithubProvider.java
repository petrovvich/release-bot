package it.petrovich.bots.release.infrastructure.providers;

import it.petrovich.bots.release.infrastructure.model.ReleaseInfoEntity;
import it.petrovich.bots.release.infrastructure.model.SourceType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.net.URL;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Comparator;

import static java.util.Optional.ofNullable;

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
                .filter(element -> !"Downloads".contains(element.text()))
                .map(element -> new ReleaseInfoEntity(getPublishDate(element), sourceType,
                        buildUrl(requestUrl, element), element.text()))
                .sorted(Comparator.comparing(ReleaseInfoEntity::getVersion, String::compareTo))
                .toList();
    }

    private String buildUrl(URL requestUrl, Element element) {
        return requestUrl.getProtocol() + "://" + requestUrl.getHost() + element.attributes().get("href");
    }

    private OffsetDateTime getPublishDate(Element element) {
        final var parent = ofNullable(element.parent())
                .map(Element::parent).map(Element::parent)
                .map(parentElem -> parentElem.getElementsByTag("relative-time"))
                .map(parentElem -> {
                    if (parentElem.size() > 0) {
                        return parentElem.get(0);
                    } else {
                        return null;
                    }
                });
        if (parent.isPresent()) {
            return OffsetDateTime.parse(parent.get().attributes().get("datetime"));
        }
        return OffsetDateTime.now();
    }
}
