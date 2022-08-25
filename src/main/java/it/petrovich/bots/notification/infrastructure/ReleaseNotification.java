package it.petrovich.bots.notification.infrastructure;

import it.petrovich.bots.release.infrastructure.model.SourceType;

public record ReleaseNotification(String libraryName, SourceType sourceType, String version, String releaseLink) {
}
