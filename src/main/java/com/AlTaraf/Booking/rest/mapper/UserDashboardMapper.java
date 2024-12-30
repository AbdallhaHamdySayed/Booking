package com.AlTaraf.Booking.rest.mapper;

import com.AlTaraf.Booking.database.entity.User;
import com.AlTaraf.Booking.rest.dto.UserDashboard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserDashboardMapper {
    @Mappings({
            @Mapping(source = "id", target = "userId"),
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "phone", target = "phone"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "roles", target = "roles"),
            @Mapping(source = "city", target = "city"),
            @Mapping(source = "ban", target = "ban"),
            @Mapping(source = "warnings", target = "warnings"),
    })
    UserDashboard toUserDashboard(User user);

    }

