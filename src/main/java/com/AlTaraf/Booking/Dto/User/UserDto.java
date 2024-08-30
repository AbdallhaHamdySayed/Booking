package com.AlTaraf.Booking.Dto.User;


import com.AlTaraf.Booking.Dto.Image.FileForProfileDTO;
import com.AlTaraf.Booking.Entity.Ads.PackageAds;
import com.AlTaraf.Booking.Entity.Role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private Boolean ban;
    private String imagePaths;
    private String username;
    private String email;
    private String phone;
    private Long cityId;
    private Date createdDate;
    private Date lastModifiedDate;
    private Set<Role> roles;
    private String deviceToken;
    private Double wallet;
    private Integer numberAds;
    private PackageAds packageAds;
}
