package api.kokonut.moodzh.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import api.kokonut.moodzh.api.dto.response.UserResponse;
import api.kokonut.moodzh.data.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "profilePicture", target = "profile")
    @Mapping(target = "createdAt", expression = "java(user.getCreatedAt().toString())")
    @Mapping(target = "lastUpdated", expression = "java(user.getUpdatedAt().toString())")
    UserResponse toResponse(User user);
}
