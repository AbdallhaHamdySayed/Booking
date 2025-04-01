package com.AlTaraf.Booking.rest.mapper;

import com.AlTaraf.Booking.database.entity.User;
import com.AlTaraf.Booking.rest.dto.UserEditDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserEditDtoMapper {

    UserEditDtoMapper INSTANCE = Mappers.getMapper(UserEditDtoMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "email", target = "email")
    User userEditDtoToUser(UserEditDto userEditDto);

    UserEditDto userToUserEditDto(User user);
}