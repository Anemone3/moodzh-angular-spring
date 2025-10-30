package api.kokonut.moodzh.util.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import api.kokonut.moodzh.api.dto.response.CollectionResponse;
import api.kokonut.moodzh.api.dto.response.ImagesResponse;
import api.kokonut.moodzh.data.model.Collections;

@Mapper(componentModel = "spring", uses = { ImagesMapper.class })
public interface CollectionMapper {

    @Mapping(source = "collections.user.id", target = "userId")
    @Mapping(source = "collections.user.username", target = "username")
    @Mapping(source = "images", target = "images")
    CollectionResponse toResponse(Collections collections, List<ImagesResponse> images);

    @Mapping(source = "collections.user.id", target = "userId")
    @Mapping(source = "collections.user.username", target = "username")
    @Mapping(target = "images", expression = "java(null)")
    CollectionResponse toMinimalResponse(Collections collections);
}