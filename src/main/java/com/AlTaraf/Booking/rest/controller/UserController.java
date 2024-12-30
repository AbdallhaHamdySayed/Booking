package com.AlTaraf.Booking.rest.controller;


import com.AlTaraf.Booking.Security.jwt.JwtUtils;
import com.AlTaraf.Booking.Security.service.UserDetailsImpl;
import com.AlTaraf.Booking.database.entity.ERole;
import com.AlTaraf.Booking.database.entity.User;
import com.AlTaraf.Booking.database.repository.UserRepository;
import com.AlTaraf.Booking.rest.dto.*;
import com.AlTaraf.Booking.rest.mapper.UserMapper;
import com.AlTaraf.Booking.service.OtpService;
import com.AlTaraf.Booking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    OtpService otpService;

    @PostMapping("/send-otp-whats")
    public ResponseEntity<?> sendOtpWhats(@RequestParam String recipient, @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {
        return otpService.sendOtpViaWhatsApp(recipient, acceptLanguageHeader);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestParam String recipient, @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {
        return otpService.sendOtp(recipient, acceptLanguageHeader);
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<?> validateOtp(@RequestParam String recipient, @RequestParam int otp,
                                         @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {


        return otpService.validateOtp(recipient, otp, acceptLanguageHeader);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterDto userRegisterDto,
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

        Set<ERole> roles = new HashSet<>(userRegisterDto.getRoles().stream()
                .map(ERole::valueOf)
                .collect(Collectors.toSet()));


        boolean existsByPhoneNumber= userService.existsByPhone(userRegisterDto.getPhoneNumber());

        if (existsByPhoneNumber) {
            CheckApiResponse response = new CheckApiResponse(409, messageSource.getMessage("authentication.message", null, LocaleContextHolder.getLocale()), false);
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(response);
        }


        try {
            User user = userService.registerUser(userRegisterDto);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse(500, "User registration failed."));
            }
            if (userRegisterDto.getRoles().contains("ROLE_SERVICE") || userRegisterDto.getRoles().contains("ROLE_ADMIN")) {
                user.setIsActive(true);
                System.out.println("user getId: " + user.getId());
                userService.setActive(user.getId());
            }

        } catch (Exception e) {
            System.out.println("Exception e: " + e.getMessage());
            e.printStackTrace();
        }
        ApiResponse response = new ApiResponse(200, messageSource.getMessage("registration.message", null, LocaleContextHolder.getLocale()));

        return ResponseEntity.ok(response);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest,
                                   @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {

        Locale locale = LocaleContextHolder.getLocale(); // Default to the locale context holder's locale

        if (acceptLanguageHeader != null && !acceptLanguageHeader.isEmpty()) {
            try {
                List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(acceptLanguageHeader);
                if (!languageRanges.isEmpty()) {
                    locale = Locale.forLanguageTag(languageRanges.get(0).getRange());
                }
            } catch (IllegalArgumentException e) {
                System.out.println("IllegalArgumentException: " + e);
            }
        }

        if (!userService.existsByPhone(loginRequest.getPhone())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(400, messageSource.getMessage("duplicate_phone.message", null, locale)));
        }

        Optional<User> userForCheckActive = userService.findByPhone(loginRequest.getPhone());

        if (userForCheckActive.isPresent()) {
            Boolean isActive = userForCheckActive.get().getIsActive();
            if (isActive == null || !isActive) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(400, messageSource.getMessage("account_not_valid.message", null, locale)));
            }
            userForCheckActive.get().setIsActive(true);
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getPhone(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            userDetails.setStayLoggedIn(loginRequest.isStayLoggedIn());

            Optional<User> optionalUser = userService.findByPhone(loginRequest.getPhone());
            if (!optionalUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse(500, messageSource.getMessage("user_not_found.message", null, locale)));
            }

            User userForDeviceToken = userRepository.findByPhoneForUser(loginRequest.getPhone());
            userForDeviceToken.setDeviceToken(loginRequest.getDeviceToken());
            userRepository.save(userForDeviceToken);

            User user = optionalUser.get();

            Set<String> userRoles = user.getRoles().stream()
                    .map(role -> role.getName().name())
                    .collect(Collectors.toSet());

            Set<String> requestRoles = loginRequest.getRoles();

            if (Collections.disjoint(userRoles, requestRoles)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(400, messageSource.getMessage("role_is_not_correct.message", null, locale)));
            }

            if (userForDeviceToken.getBan()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse(200, messageSource.getMessage("user_ban.message", null, locale)));
            }

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(
                    jwtUtils.generateJwtToken(authentication, loginRequest.isStayLoggedIn()),
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    userDetails.getPhone(),
                    userDetails.getCity(),
                    roles));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(401, messageSource.getMessage("invalid_password.message", null, locale)));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id,
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
        User user = userService.getUserById(id);
        if (user.getNumberAds() == 0) {
            user.setNumberAds(null);
            user.setPackageAds(null);
            userService.updateUser(user);
        }

        if (user != null) {
            UserDto userDto = userMapper.INSTANCE.userToUserDto(user);
            return ResponseEntity.ok(userDto);
        } else {
            ApiResponse response = new ApiResponse(404, messageSource.getMessage("not_found.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @PatchMapping("/edit/{userId}")
    public ResponseEntity<?> editUser(@PathVariable Long userId,
                                      @Valid @RequestBody UserEditDto userEditDto,
                                      @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {
        try {
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

            // Retrieve the user by ID
            User existingUser = userService.getUserById(userId);
            boolean isEmailAvailable = userService.existsByEmail(userEditDto.getEmail());
            // Update the user information conditionally based on non-null values in the UserEditDto
            if (userEditDto.getUsername() != null) {
                existingUser.setUsername(userEditDto.getUsername());
            }

            if (userEditDto.getEmail() != null && !Objects.equals(existingUser.getEmail(), userEditDto.getEmail())) {
                System.out.println("yo");
                if (isEmailAvailable){
                    CheckApiResponse response = new CheckApiResponse(204, messageSource.getMessage("email_taken.message", null, LocaleContextHolder.getLocale()), false);
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(response);
                } else {
                    existingUser.setEmail(userEditDto.getEmail());
                }
            }

            if (userEditDto.getPhone() != null) {
                existingUser.setPhone(userEditDto.getPhone());
            }

            // Save the updated user
            userService.updateUser(existingUser);

            ApiResponse response = new ApiResponse(200, messageSource.getMessage("user_updated.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, messageSource.getMessage("error_updating_user.message", null, LocaleContextHolder.getLocale())));
        }
    }

    @PatchMapping("/{userId}/wallet")
    public ResponseEntity<?> updateUserWallet(@PathVariable Long userId, @RequestParam(name = "wallet") Double wallet) {
        try {
            User updatedUser = userService.updateWallet(userId, wallet);
            ApiResponse apiResponse = new ApiResponse(200, "تم تعديل محفظة المستخدم");
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, "حدث خطأ ولم يتم التعديل علي محفظة المستخدم"));
        }
    }

    @PatchMapping("/active/{userId}")
    public ResponseEntity<?> updateUserActive(@PathVariable Long userId) {
        try {
            User updatedUser = userService.updateActive(userId);
            ApiResponse apiResponse = new ApiResponse(200, "تم تعديل محفظة المستخدم");
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, "حدث خطأ ولم يتم التعديل علي محفظة المستخدم"));
        }
    }

}