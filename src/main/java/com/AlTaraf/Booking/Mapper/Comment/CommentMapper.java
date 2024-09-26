package com.AlTaraf.Booking.Mapper.Comment;

import com.AlTaraf.Booking.Dto.Comment.CommentRequest;
import com.AlTaraf.Booking.Entity.Comment;
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