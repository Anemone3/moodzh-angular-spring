package api.kokonut.moodzh.data.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorPost {
    @Column(name = "author_id",length = 36,nullable = true)
    private String authorId;

    @Column(name = "username",length = 100,nullable = true)
    private String username;
    @Column(name = "avatar_url", nullable = true)
    private String avatarUrl;
}
