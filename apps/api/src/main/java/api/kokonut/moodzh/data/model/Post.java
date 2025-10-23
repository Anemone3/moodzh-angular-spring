package api.kokonut.moodzh.data.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import api.kokonut.moodzh.data.enums.ContentType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Aun podemos manejar el userId aqui, por ejemplo al subir una imagen
    // automaticamente generamos
    // el post, y esto ayudaria a obtener datos de esta tabla sin necesidad de
    // transaccionar

    @Column(nullable = false, unique = true)
    private String slug;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentType contentType;

    @Column(name = "content_id", nullable = false, length = 36)
    private String contentId;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final Set<Favorites> likedBy = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final Set<PostCollections> postCollections = new HashSet<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private final Set<Notification> postNotifications = new HashSet<>();

    @Builder.Default
    @Column(name = "favorites_count", nullable = false, columnDefinition = "default 0")
    private int favoritesCount = 0;
    @Builder.Default
    @Column(name = "visualizaciones_count", nullable = false)
    private int visualizacionesCount = 0;
    @Builder.Default
    @Column(name = "downloads_count", nullable = false)
    private int downloadsCount = 0;
    @Builder.Default
    @Column(name = "collected_count", nullable = false)
    private int collectedCount = 0;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

}
