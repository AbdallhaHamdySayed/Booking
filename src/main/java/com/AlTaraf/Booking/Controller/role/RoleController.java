package com.AlTaraf.Booking.Controller.role;

import com.AlTaraf.Booking.Dto.Roles.RoleDto;
import com.AlTaraf.Booking.Entity.Role.Role;
import com.AlTaraf.Booking.Mapper.RoleMapper;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    MessageSource messageSource;

    // Get a role by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Long id,
                                         @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {

        Locale locale = LocaleContextHolder.getLocale(); // Default to the locale context holder's locale

        if (acceptLanguageHeader != null && !acceptLanguageHeader.isEmpty()) {
            try {
                List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(acceptLanguageHeader);
                if (!languageRanges.isEmpty()) {
                    locale = Locale.forLanguageTag(languageRanges.get(0).getRange());
                }
            } catch (IllegalArgumentException e) {
                // Handle the exception if needed
                System.out.println("IllegalArgumentException: " + e);
            }
        }

        Role role = roleService.getRoleById(id);
        if (role != null) {
            RoleDto roleDto = RoleMapper.INSTANCE.roleToRoleDto(role);
            return ResponseEntity.ok(roleDto);
        } else {
            ApiResponse response = new ApiResponse(404,  messageSource.getMessage("not_found.messagee", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Get all roles
    @GetMapping("/all")
    public ResponseEntity<?> getAllRoles(@RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {

        Locale locale = LocaleContextHolder.getLocale(); // Default to the locale context holder's locale

        if (acceptLanguageHeader != null && !acceptLanguageHeader.isEmpty()) {
            try {
                List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(acceptLanguageHeader);
                if (!languageRanges.isEmpty()) {
                    locale = Locale.forLanguageTag(languageRanges.get(0).getRange());
                }
            } catch (IllegalArgumentException e) {
                // Handle the exception if needed
                System.out.println("IllegalArgumentException: " + e);
            }
        }

        List<Role> roles = roleService.getAllRoles();
        if (!roles.isEmpty()) {
            List<RoleDto> roleDtos = RoleMapper.INSTANCE.rolesToRoleDtos(roles);
            return ResponseEntity.ok(roleDtos);
        } else {
            ApiResponse response = new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale()));

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
    }

}

