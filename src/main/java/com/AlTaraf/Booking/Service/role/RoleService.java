package com.AlTaraf.Booking.Service.role;


import com.AlTaraf.Booking.Entity.Role.Role;

import java.util.List;

public interface RoleService {
    Role getRoleById(Long id);
    List<Role> getAllRoles();

}

