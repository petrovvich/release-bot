package it.petrovich.bots.release.infrastructure.model;

import io.micronaut.data.annotation.GeneratedValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "release_config")
public class SourceConfigEntity {
    @Id
    @Column(columnDefinition = "uuid")
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
