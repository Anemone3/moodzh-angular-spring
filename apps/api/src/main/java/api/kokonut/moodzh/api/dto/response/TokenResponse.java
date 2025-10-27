package api.kokonut.moodzh.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
    private String access_token;
    private String token_type;
    private Date expires_in;
    private String refresh_token;
}
