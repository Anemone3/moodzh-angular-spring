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
public class LocationUser {
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;
}
