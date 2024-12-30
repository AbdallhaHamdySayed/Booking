package com.AlTaraf.Booking.rest.mapper;


import com.AlTaraf.Booking.database.entity.Notifications;
import com.AlTaraf.Booking.rest.dto.PushNotificationForAllResponse;
import com.AlTaraf.Booking.rest.dto.PushNotificationRequestForAll;
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
