package api.kokonut.moodzh.core.strategy.contenttype;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import api.kokonut.moodzh.api.dto.response.ImagesResponse;
import api.kokonut.moodzh.data.model.Post;

public interface IContentTypeStrategy {
    List<ImagesResponse> getImagesFromCore(List<Post> post);

    CompletableFuture<ImagesResponse> getOneImageFromCore(Post post);

    final class ContentTypeNames {
        public static final String LOCAL = "local";
        public static final String EXTERNAL = "external";
    }
}
