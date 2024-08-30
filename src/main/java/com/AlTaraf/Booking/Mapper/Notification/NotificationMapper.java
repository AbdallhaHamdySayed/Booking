package com.AlTaraf.Booking.Mapper.Notification;

import com.AlTaraf.Booking.Dto.Notifications.PushNotificationRequest;
import com.AlTaraf.Booking.Dto.Notifications.Response.PushNotificationResponse;
import com.AlTaraf.Booking.Entity.Notifications.Notifications;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    @Mapping(source = "title", target = "title")
    @Mapping(source = "body", target = "body")
    @Mapping(source = "userId", target = "user.id")
    Notifications dtoToEntity(PushNotificationRequest pushNotificationRequest);


    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "body", target = "body")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "seen", target = "seen")
    @Mapping(source = "lang", target = "lang")
    @Mapping(source = "elapsedTime", target = "elapsedTime")
    PushNotificationResponse entityToDto(Notifications pushNotificationRequest);
}
