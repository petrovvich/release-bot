package it.petrovich.bots.release.infrastructure.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.type.PostgresUUIDType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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

    public ReleaseInfoEntity(SourceType type, String releaseUrl, String version) {
        this.type = type;
        this.releaseUrl = releaseUrl;
        this.version = version;
    }
}
