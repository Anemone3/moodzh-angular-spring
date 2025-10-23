package api.kokonut.moodzh.api.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public record ImagesResponse(String id, String title, AuthorDTO author, String description, String urlImage, int height,
        int width, List<String> tags) {
    public ImagesResponse(String id, String title, AuthorDTO author, String description, String urlImage, int height,
            int width) {

        this(id, title, author, description, urlImage, height, width, null);
    }

}
