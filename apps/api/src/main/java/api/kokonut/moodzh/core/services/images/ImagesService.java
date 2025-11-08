package api.kokonut.moodzh.core.services.images;

import java.util.List;

import api.kokonut.moodzh.data.model.Images;

public interface ImagesService {
    void getImages();

    void getExternalImages();

    List<Images> getUserImages(String userId);

    void getImagesExternalByTags(String tags);

    void createImage(String userId, String imageUrl);
}
