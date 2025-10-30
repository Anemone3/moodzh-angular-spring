package api.kokonut.moodzh.api.dto.response;

import api.kokonut.moodzh.data.embeddable.LinkSocial;
import api.kokonut.moodzh.data.embeddable.LocationUser;
import lombok.Builder;

@Builder
public record UserResponse(String id, String email, String username, String profile, String providerId, String provider,
        LinkSocial linkSocial, LocationUser location, String createdAt, String lastUpdated) {
}
