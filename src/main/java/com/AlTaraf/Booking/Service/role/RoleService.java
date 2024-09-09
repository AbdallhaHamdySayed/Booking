package com.AlTaraf.Booking.Service.role;


import com.AlTaraf.Booking.Entity.Role.Role;
import com.AlTaraf.Booking.Entity.enums.ERole;

import java.util.List;

public interface RoleService {
    Role getRoleById(Long id);
    List<Role> getAllRoles();

}

