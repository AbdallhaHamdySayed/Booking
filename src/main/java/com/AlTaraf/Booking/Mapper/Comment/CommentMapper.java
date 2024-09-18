package com.AlTaraf.Booking.Mapper.Comment;

import com.AlTaraf.Booking.Dto.Comment.CommentRequest;
import com.AlTaraf.Booking.Entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "content", target = "content")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "unitId", target = "unit.id")
    Comment toComment(CommentRequest commentRequest);


}