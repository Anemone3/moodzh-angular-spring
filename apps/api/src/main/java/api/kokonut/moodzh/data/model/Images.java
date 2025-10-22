package api.kokonut.moodzh.data.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import api.kokonut.moodzh.util.converters.StringSetConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @Column(name="image_url", nullable = false)
    private String url;

    @Column(name = "tags", columnDefinition = "TEXT")
    @Convert(converter = StringSetConverter.class)
    private Set<String> tags = new HashSet<>();


    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

}
