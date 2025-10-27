package api.kokonut.moodzh.core.strategy.contenttype.impls;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import api.kokonut.moodzh.api.dto.response.ImagesResponse;
import api.kokonut.moodzh.core.strategy.contenttype.IContentTypeStrategy;
import api.kokonut.moodzh.data.model.Post;
import api.kokonut.moodzh.data.repository.ExternalImageRepository;
import api.kokonut.moodzh.util.mapper.ImagesMapper;
import lombok.AllArgsConstructor;

@Service(IContentTypeStrategy.ContentTypeNames.EXTERNAL)
@AllArgsConstructor
public class ExternalContentService implements IContentTypeStrategy {

    private final ExternalImageRepository externalImageRepository;
    private final ImagesMapper imagesMapper;
    @Qualifier("dbTaskExecutor")
    private final Executor dbExecutor;

    @Override
    public List<ImagesResponse> getImagesFromCore(List<Post> post) {
        return post.stream().map(item -> {
            var image = externalImageRepository.findByExternalId(item.getContentId());
            return imagesMapper.toResponse(image.get(), image.get().getHeight(), image.get().getWidth());
        }).toList();
    }

    @Override
    public CompletableFuture<ImagesResponse> getOneImageFromCore(Post post) {

        return CompletableFuture.supplyAsync(() -> {
            var image = externalImageRepository.findByExternalId(post.getContentId());
            return image.map(externalImages -> imagesMapper.toResponse(externalImages, 0, 0)).orElse(null);
        }, dbExecutor);
    }
}
