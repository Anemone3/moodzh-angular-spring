package api.kokonut.moodzh.core.strategy.contenttype.impls;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.springframework.stereotype.Service;

import api.kokonut.moodzh.api.dto.response.ImagesResponse;
import api.kokonut.moodzh.core.strategy.contenttype.IContentTypeStrategy;
import api.kokonut.moodzh.data.model.Post;
import api.kokonut.moodzh.data.repository.ImageRepository;
import api.kokonut.moodzh.util.mapper.ImagesMapper;
import lombok.AllArgsConstructor;

@Service(IContentTypeStrategy.ContentTypeNames.LOCAL)
@AllArgsConstructor
public class LocalContentService implements IContentTypeStrategy {

    private final ImageRepository imageRepository;
    private final ImagesMapper imagesMapper;
    private final Executor dbTaskExecutor;

    @Override
    public List<ImagesResponse> getImagesFromCore(List<Post> post) {
        return post.stream().map(item -> {
            var image = imageRepository.findById(item.getContentId());
            return imagesMapper.toResponse(image.get(), 0, 0);
        }).toList();

    }

    /* Siempre usaré este método porque solo se conseguira el Id dentro del post */
    @Override
    public CompletableFuture<ImagesResponse> getOneImageFromCore(Post post) {

        return CompletableFuture.supplyAsync(() -> {
            var image = imageRepository.findById(post.getContentId());
            if (!image.isPresent()) {
                return null;
            }
            return imagesMapper.toResponse(image.get(), 0, 0);
        }, dbTaskExecutor);
    }
}
