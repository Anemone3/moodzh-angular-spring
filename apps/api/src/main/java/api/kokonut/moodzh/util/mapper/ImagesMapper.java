package api.kokonut.moodzh.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import api.kokonut.moodzh.api.dto.response.ImagesResponse;
import api.kokonut.moodzh.data.model.ExternalImages;
import api.kokonut.moodzh.data.model.Images;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = { AuthorDTOMapper.class })
public interface ImagesMapper {

    @Mapping(source = "images.user", target = "author")
    @Mapping(source = "altura", target = "height")
    @Mapping(source = "ancho", target = "width")
    @Mapping(source = "images.url", target = "urlImage")
    @Mapping(source = "images.tags", target = "tags")
    ImagesResponse toResponse(Images images, int altura, int ancho);

    @Mapping(source = "altura", target = "height")
    @Mapping(source = "ancho", target = "width")
    @Mapping(source = "externalImages.url", target = "urlImage")
    @Mapping(source = "externalImages.author", target = "author")
    @Mapping(source = "externalImages.tags", target = "tags")
    @Mapping(source = "externalImages.description", target = "description")
    ImagesResponse toResponse(ExternalImages externalImages, int altura, int ancho);

}