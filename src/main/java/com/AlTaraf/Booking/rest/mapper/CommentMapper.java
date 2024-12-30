package com.AlTaraf.Booking.rest.mapper;

import com.AlTaraf.Booking.database.entity.Comment;
import com.AlTaraf.Booking.rest.dto.CommentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "content", target = "content")
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "phoneNumber", target = "phoneUser")
    @Mapping(source = "fileDownloadUri", target = "fileDownloadUri")
    @Mapping(source = "unitId", target = "unit.id")
    Comment toComment(CommentRequest commentRequest);


}