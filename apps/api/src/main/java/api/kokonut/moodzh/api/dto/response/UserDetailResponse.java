package api.kokonut.moodzh.api.dto.response;

import api.kokonut.moodzh.data.embeddable.LinkSocial;
import api.kokonut.moodzh.data.embeddable.LocationUser;

public record UserDetailResponse(String id, String email, String username, String profile, String providerId,
        String provider,
        LinkSocial socialLinks, LocationUser location, String createdAt, String lastUpdated) {
}