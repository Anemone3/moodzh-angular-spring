package api.kokonut.moodzh.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorDTO {
    private String id;
    private String username;
}
