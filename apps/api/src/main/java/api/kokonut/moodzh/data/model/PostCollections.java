package api.kokonut.moodzh.data.model;

import java.time.Instant;

import api.kokonut.moodzh.data.embeddable.PostCollectionId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


@Entity
@Table(name = "post_collections",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"post_id", "collection_id"}, name = "uc_post_collection")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCollections {

    @EmbeddedId
    private PostCollectionId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId") //mapear igual como asignamos en el PostCollectionId
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("collectionId")
    @JoinColumn(name = "collection_id")
    private Collections collection;


    @CreationTimestamp
    @Column(name = "added_at", nullable = false)
    private Instant addedAt;


}
