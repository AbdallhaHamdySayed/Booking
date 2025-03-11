package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.Role;
import com.AlTaraf.Booking.database.repository.UserRolesRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserRolesService {

    @Autowired
    UserRolesRepository userRolesRepository;

    public List<Role> getRoleByUserId(Long userId) {
        return userRolesRepository.findRoleByUserId(userId);
    }
}
