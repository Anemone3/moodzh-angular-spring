package api.kokonut.moodzh.api.dto.response;

import lombok.Builder;

@Builder
public record UserResponse(String id, String username, String profile,TokenResponse token) {
}
