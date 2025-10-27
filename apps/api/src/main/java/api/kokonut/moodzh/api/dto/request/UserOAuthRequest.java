package api.kokonut.moodzh.api.dto.request;

import lombok.Builder;

@Builder
public record UserOAuthRequest(String username, String email, String password, String profileUrl, String provider, String providerId) {
}
