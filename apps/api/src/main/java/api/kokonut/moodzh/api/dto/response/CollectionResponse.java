package api.kokonut.moodzh.api.dto.response;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public record CollectionResponse(String id, String userId, String username, String name, String description,
        Instant createdAt, List<ImagesResponse> images) {

    public CollectionResponse(String id, String userId, String username, String name, String description,
            Instant createdAt) {
        this(id, userId, username, name, description, createdAt, Collections.emptyList());
    }

}