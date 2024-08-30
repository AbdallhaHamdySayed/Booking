package com.AlTaraf.Booking.Service.user;

import com.AlTaraf.Booking.Dto.User.UserRegisterDto;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.enums.ERole;
import com.AlTaraf.Booking.Exception.InsufficientFundsException;
import com.AlTaraf.Booking.Payload.request.PasswordResetDto;
import com.AlTaraf.Booking.Payload.response.CounterUser;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    Boolean existsByEmailAndRolesOrPhoneNumberAndRoles(String phone, Set<ERole> roleNames);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);

    User registerUser(UserRegisterDto userRegisterDto);

    boolean isDuplicatePhoneNumber( String phone);

    User getUserById(Long id);

    void resetPasswordByPhone(String phone, PasswordResetDto passwordResetDto);

    void updateUser(User user);

    Optional<User> findByPhone(String phone);

    void deleteUserAndAssociatedEntities(Long userId);

    User setPackageAdsForUser(Long userId, Long packageAdsId) throws InsufficientFundsException;

    CounterUser getCountUser();

    List<User> getAllUser();

    List<User> getAllByRolesName(ERole roleName);

    void deleteUsersWithIsActiveNull();
}

