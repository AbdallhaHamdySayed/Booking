package com.AlTaraf.Booking.rest.mapper;

import com.AlTaraf.Booking.database.entity.Unit;
import com.AlTaraf.Booking.rest.dto.UnitDashboard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UnitDashboardMapper {
    @Mappings({
            @Mapping(source = "id", target = "unitId"),
            @Mapping(source = "accommodationType", target = "accommodationType"),
            @Mapping(source = "unit.user.id", target = "userId"),
            @Mapping(source = "unit.nameUnit", target = "unitName"),
            @Mapping(source = "unit.user.username", target = "traderName"),
            @Mapping(source = "unit.user.phone", target = "traderPhone"),
            @Mapping(source = "unit.user.email", target = "traderEmail"),
            @Mapping(source = "unit.user.ban", target = "ban"),
            @Mapping(source = "unit.region", target = "regionDto"),
            @Mapping(source = "unit.city", target = "cityDtoSample"),
            @Mapping(source = "statusUnit", target = "statusUnit"),
    })
    UnitDashboard toUnitDashboard(Unit unit);

    }

