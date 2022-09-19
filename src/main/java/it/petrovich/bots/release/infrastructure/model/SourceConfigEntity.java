package it.petrovich.bots.release.infrastructure.model;

import io.micronaut.data.annotation.GeneratedValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.PostgresUUIDType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "release_config")
@TypeDefs({
        @TypeDef(name = "pg-uuid", defaultForType = UUID.class, typeClass = PostgresUUIDType.class)
})
public class SourceConfigEntity {
    @Id
    @Column(columnDefinition = "pg-uuid")
    @GeneratedValue(GeneratedValue.Type.AUTO)
    private UUID id;
    @CreationTimestamp
    private OffsetDateTime creationDate;
    @UpdateTimestamp
    private OffsetDateTime updateDate;
    @Enumerated(EnumType.STRING)
    private SourceType type;
    private String url;
    private String libraryName;
}
