package com.AlTaraf.Booking.Mapper.Unit.Dashboard;

import com.AlTaraf.Booking.Dto.Unit.UnitDashboard;
import com.AlTaraf.Booking.Dto.User.UserDashboard;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.unit.Unit;
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

