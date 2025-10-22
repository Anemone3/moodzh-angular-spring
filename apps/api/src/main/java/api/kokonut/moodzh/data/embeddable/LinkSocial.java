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
public class LinkSocial {
    @Column(name = "instagram", length = 100, nullable = true)
    private String instagram;
    @Column(name = "facebook", length = 100, nullable = true)
    private String facebook;
    @Column(name = "twitter", length = 100, nullable = true)
    private String twitter;
    @Column(name = "youtube", length = 100, nullable = true)
    private String youtube;
    @Column(name = "linkedin", length = 100, nullable = true)
    private String linkedin;
    @Column(name = "website", length = 100, nullable = true)
    private String website;
}
