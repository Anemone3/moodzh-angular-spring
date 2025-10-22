package api.kokonut.moodzh.data.embeddable;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class PostCollectionId {
    private Long postId;
    private String collectionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostCollectionId that = (PostCollectionId) o;
        return Objects.equals(postId, that.postId) &&
                Objects.equals(collectionId, that.collectionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, collectionId);
    }
}
