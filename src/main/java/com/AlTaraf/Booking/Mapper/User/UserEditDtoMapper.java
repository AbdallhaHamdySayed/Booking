package com.AlTaraf.Booking.Mapper.User;

import com.AlTaraf.Booking.Dto.User.UserEditDto;
import com.AlTaraf.Booking.Entity.User.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserEditDtoMapper {

    UserEditDtoMapper INSTANCE = Mappers.getMapper(UserEditDtoMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    User userEditDtoToUser(UserEditDto userEditDto);

    UserEditDto userToUserEditDto(User user);
}