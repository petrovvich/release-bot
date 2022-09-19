package it.petrovich.bots.release.infrastructure.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.type.PostgresUUIDType;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "release_info")
@TypeDefs({
        @TypeDef(name = "pg-uuid", defaultForType = UUID.class, typeClass = PostgresUUIDType.class)
})
public class ReleaseInfoEntity {
    @Id
    @Column(columnDefinition = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @CreationTimestamp
    private OffsetDateTime creationDate;
    @Column(columnDefinition = "pg-uuid")
    private UUID configId;
    @Enumerated(EnumType.STRING)
    private SourceType type;
    private String releaseUrl;
    private String version;
    private OffsetDateTime publishDate;
    @Enumerated(EnumType.STRING)
    private NotificationState state;

    public ReleaseInfoEntity(OffsetDateTime publishDate, SourceType type, String releaseUrl, String version) {
        this.publishDate = publishDate;
        this.type = type;
        this.releaseUrl = releaseUrl;
        this.version = version;
    }
}
