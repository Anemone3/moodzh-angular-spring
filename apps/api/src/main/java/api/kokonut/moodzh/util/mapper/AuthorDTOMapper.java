package api.kokonut.moodzh.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import api.kokonut.moodzh.api.dto.response.AuthorDTO;
import api.kokonut.moodzh.data.embeddable.AuthorPost;
import api.kokonut.moodzh.data.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthorDTOMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    AuthorDTO toDto(User user);

    @Mapping(source = "authorId", target = "id")
    @Mapping(source = "username", target = "username")
    AuthorDTO toDto(AuthorPost authorPost);
}
