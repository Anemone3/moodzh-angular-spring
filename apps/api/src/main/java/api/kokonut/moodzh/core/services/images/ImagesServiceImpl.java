package api.kokonut.moodzh.core.services.images;

import java.util.List;

import org.springframework.stereotype.Service;

import api.kokonut.moodzh.data.model.Images;
import api.kokonut.moodzh.data.repository.ExternalImageRepository;
import api.kokonut.moodzh.data.repository.ImageRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ImagesServiceImpl implements ImagesService {

    private final ImageRepository imageRepository;
    private final ExternalImageRepository externalImageRepository;

    @Override
    public void getImages() {
        return;
    }

    @Override
    public void getExternalImages() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getExternalImages'");
    }

    @Override
    public List<Images> getUserImages(String userId) {
        return imageRepository.findAllByUserId(userId);
    }

    @Override
    public void getImagesExternalByTags(String tags) {
        return;
    }

    @Override
    public void createImage(String userId, String imageUrl) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createImage'");
    }

}
