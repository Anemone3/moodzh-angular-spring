package api.kokonut.moodzh.core.services.collections;

import java.util.List;

import org.springframework.data.domain.Pageable;

import api.kokonut.moodzh.api.dto.request.CollectionRequest;
import api.kokonut.moodzh.api.dto.response.CollectionResponse;

public interface CollectionService {
    List<CollectionResponse> getCollections(Pageable pageable);

    List<CollectionResponse> getUserCollections(String userId);

    CollectionResponse getCollectionById(String id);

    CollectionResponse createCollection(CollectionRequest request, String userId);

    void updateCollection(String id, String name);// request collection body

    void deleteCollection(String id);

    void saveImageOnCollection(String id, String imageId);// request collection body and images source

    void removeImageFromCollection(String id, String imageId);
}
