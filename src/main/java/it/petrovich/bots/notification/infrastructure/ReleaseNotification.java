package it.petrovich.bots.notification.infrastructure;

import it.petrovich.bots.release.infrastructure.model.SourceType;

import java.util.UUID;

public record ReleaseNotification(UUID releaseId,
                                  String libraryName,
                                  SourceType sourceType,
                                  String version,
                                  String releaseLink) {
}
