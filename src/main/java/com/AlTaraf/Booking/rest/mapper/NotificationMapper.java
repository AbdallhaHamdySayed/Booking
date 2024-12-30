package com.AlTaraf.Booking.rest.mapper;

import com.AlTaraf.Booking.database.entity.Notifications;
import com.AlTaraf.Booking.rest.dto.PushNotificationRequest;
import com.AlTaraf.Booking.rest.dto.PushNotificationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    @Mapping(source = "title", target = "title")
    @Mapping(source = "body", target = "body")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "unitId", target = "unitId")
    @Mapping(source = "reservationId", target = "reservationId")
    @Mapping(source = "adsId", target = "adsId")
    Notifications dtoToEntity(PushNotificationRequest pushNotificationRequest);


    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "body", target = "body")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "seen", target = "seen")
    @Mapping(source = "lang", target = "lang")
    @Mapping(source = "elapsedTime", target = "elapsedTime")
    @Mapping(source = "unitId", target = "unitId")
    @Mapping(source = "reservationId", target = "reservationId")
    @Mapping(source = "adsId", target = "adsId")
    PushNotificationResponse entityToDto(Notifications pushNotificationRequest);
}
