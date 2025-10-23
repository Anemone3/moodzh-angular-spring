package api.kokonut.moodzh.util.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import api.kokonut.moodzh.api.dto.response.CollectionResponse;
import api.kokonut.moodzh.api.dto.response.ImagesResponse;
import api.kokonut.moodzh.data.model.Collections;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = { ImagesMapper.class })
public interface CollectionMapper {

    @Mapping(source = "collections.user.id", target = "userId")
    @Mapping(source = "collections.user.username", target = "username")
    @Mapping(source = "images", target = "images")
    CollectionResponse toResponse(Collections collections, List<ImagesResponse> images);

    // ----------------------------------------------------------------------------------
    // Mapeo Mínimo: Cuando la lista de imágenes debe ser NULL (para omitirla en el
    // JSON)
    // Se usa este método cuando no se quieren cargar las relaciones (ej. listado
    // rápido).
    // Se renombra para evitar ambigüedad en MapStruct.
    // ----------------------------------------------------------------------------------
    @Mapping(source = "collections.user.id", target = "userId")
    @Mapping(source = "collections.user.username", target = "username")
    @Mapping(target = "images", expression = "java(null)")
    CollectionResponse toMinimalResponse(Collections collections);
}