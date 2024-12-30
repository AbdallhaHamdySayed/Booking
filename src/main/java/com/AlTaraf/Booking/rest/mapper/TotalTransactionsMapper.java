package com.AlTaraf.Booking.rest.mapper;

import com.AlTaraf.Booking.database.entity.TotalTransactions;
import com.AlTaraf.Booking.rest.dto.TotalTransactionsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TotalTransactionsMapper {

    TotalTransactionsMapper INSTANCE = Mappers.getMapper(TotalTransactionsMapper.class);

    @Mapping(target = "totalTransactions", source = "totalTransactions")
    @Mapping(target = "totalReservationsTransactions", source = "totalReservationsTransactions")
    @Mapping(target = "totalSubscriptionsTransactions", source = "totalSubscriptionsTransactions")
    TotalTransactions toEntity(TotalTransactionsDto totalTransactionsDto);

    @Mapping(target = "totalTransactions", source = "totalTransactions")
    @Mapping(target = "totalReservationsTransactions", source = "totalReservationsTransactions")
    @Mapping(target = "totalSubscriptionsTransactions", source = "totalSubscriptionsTransactions")
    TotalTransactionsDto toDto(TotalTransactions totalTransactions);

}
