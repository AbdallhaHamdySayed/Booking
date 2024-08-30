package com.AlTaraf.Booking.Mapper.User;


import com.AlTaraf.Booking.Dto.User.UserDto;
import com.AlTaraf.Booking.Entity.User.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

//@Mapper(componentModel = "spring")
//public interface userMapper {
//
//    userMapper INSTANCE = Mappers.getMapper(userMapper.class);
//
//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "username", target = "username")
//    @Mapping(source = "email", target = "email")
//    @Mapping(source = "phone", target = "phone")
//    @Mapping(source = "city.id", target = "cityId")
////    @Mapping(source = "createdDate", target = "createdDate")
////    @Mapping(source = "lastModifiedDate", target = "lastModifiedDate")
//    UserDto userToUserDto(User user);
//
//}
