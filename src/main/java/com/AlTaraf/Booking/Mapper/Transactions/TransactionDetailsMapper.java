package com.AlTaraf.Booking.Mapper.Transactions;

import com.AlTaraf.Booking.Dto.Transactions.TransactionDetailsDto;
import com.AlTaraf.Booking.Entity.Transactions.TransactionsDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionDetailsMapper {

    TransactionDetailsMapper INSTANCE = Mappers.getMapper(TransactionDetailsMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "date", source = "date")
    @Mapping(target = "transactions", source = "transactions")
    @Mapping(target = "gatewayEnglishName", source = "gatewayEnglishName")
    @Mapping(target = "gatewayArabicName", source = "gatewayArabicName")
    @Mapping(target = "value", source = "value")
    TransactionDetailsDto toDto(TransactionsDetail transactionsDetail);

}
