package com.AlTaraf.Booking.Mapper.Notification;

import com.AlTaraf.Booking.Dto.Notifications.PushNotificationRequestForAll;
import com.AlTaraf.Booking.Dto.Notifications.Response.PushNotificationForAllResponse;
import com.AlTaraf.Booking.Entity.Notifications.Notifications;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NotificationForAllMapper {
    NotificationForAllMapper INSTANCE = Mappers.getMapper(NotificationForAllMapper.class);

    @Mapping(source = "title", target = "title")
    @Mapping(source = "body", target = "body")
    Notifications dtoToEntity(PushNotificationRequestForAll pushNotificationRequestForAll);


    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "body", target = "body")
    PushNotificationForAllResponse entityToDto(Notifications pushNotificationRequest);
}
