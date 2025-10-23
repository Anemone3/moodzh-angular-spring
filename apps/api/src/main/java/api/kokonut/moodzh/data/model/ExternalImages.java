package api.kokonut.moodzh.data.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import api.kokonut.moodzh.data.embeddable.AuthorPost;
import api.kokonut.moodzh.util.converters.StringSetConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Esta clase solo será usada mediante una transaccion, despues de activarse una interracion
// del usuario como favoritos,o hacer un post, se crea aqui para manejar datos de manera rapida

@Entity
@Table(name = "external_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalImages {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "api_source", nullable = false)
    private String apiSource;

    @Column(name = "external_id", nullable = false, unique = true)
    private String externalId;

    @Column(name = "original_url", nullable = false, unique = true)
    private String url;
    @Column(nullable = false)
    private String title;

    private int height;
    private int width;

    @Column(nullable = true)
    private String description;

    @Column(name = "tags", columnDefinition = "TEXT")
    @Convert(converter = StringSetConverter.class)
    private final Set<String> tags = new HashSet<>();

    @Embedded
    private AuthorPost author;

    @CreationTimestamp
    @Column(name = "metadata_cache_at", nullable = false)
    private LocalDateTime metadataCacheAt;
}
