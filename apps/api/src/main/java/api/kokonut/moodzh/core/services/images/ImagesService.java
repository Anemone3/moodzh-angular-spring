package api.kokonut.moodzh.core.services.images;

public interface ImagesService {
    void getImages();
    void getExternalImages();
    void getUserImages(String userId);
    void getImagesExternalByTags(String tags);
    void createImage(String userId, String imageUrl);
}
