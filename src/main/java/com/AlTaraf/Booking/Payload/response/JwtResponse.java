package com.AlTaraf.Booking.Payload.response;

import com.AlTaraf.Booking.Entity.cityAndregion.City;

import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String phone;
    private City city;
    private List<String> role;

    public JwtResponse() {
    }

    public JwtResponse(String jwt,
                       Long id,
                       String username,
                       String email,
                       String phone,
                       City city,
                       List<String> roles) {
        this.token = jwt;
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.role = roles;
        // Assuming RoleDto has a constructor that accepts three parameters (id, roleName, arabicRoleName)
//        this.roles = roles.stream().map(role -> new RoleDto(role)).collect(Collectors.toList());

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }
}