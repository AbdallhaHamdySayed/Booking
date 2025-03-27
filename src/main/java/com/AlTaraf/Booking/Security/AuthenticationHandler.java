package com.AlTaraf.Booking.Security;


import com.AlTaraf.Booking.Security.jwt.JwtService;
import com.AlTaraf.Booking.Security.token.Token;
import com.AlTaraf.Booking.Security.token.TokenRepository;
import com.AlTaraf.Booking.Security.token.TokenType;
import com.AlTaraf.Booking.database.entity.User;
import com.AlTaraf.Booking.database.repository.UserRepository;
import com.AlTaraf.Booking.response.ErrorResponse;
import com.AlTaraf.Booking.response.SuccessResponse;
import com.AlTaraf.Booking.response.base.BaseResponse;
import com.AlTaraf.Booking.response.keys.ResponseKeys;
import com.AlTaraf.Booking.rest.mapper.UserMapper;
import com.AlTaraf.Booking.service.UserService;
import com.AlTaraf.Booking.support.utils.DateUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationHandler {
    private final UserRepository userRepository;
    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    private static final Logger log = LoggerFactory.getLogger(AuthenticationHandler.class); // ✅ Manually define logger

    public BaseResponse mobileAuthenticate(AuthenticationRequest request) {
        System.out.println("***************** Before Auth Manager ***********************");
        if (!userService.existsByPhone(request.getPhone())) {
            return new ErrorResponse(ResponseKeys.NUMBER_NOT_REGISTERED);
        }

        try {
            System.out.println("request.getPhone(): " + request.getPhone());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getPhone(), request.getPassword()));
        } catch (Exception e) {
            log.error("Authentication failed: ", e);
            System.out.println("---------------------------------------------");
            System.out.println("Authentication failed: " + e.getMessage());
            System.out.println("---------------------------------------------");
            System.out.println("Authentication Exception: " + e);
            System.out.println("---------------------------------------------");
            return new ErrorResponse(ResponseKeys.PASSWORD_NOT_CORRECT);
//            throw e;
        }


        System.out.println("************* After Auth Manager ********************");
        var user = userRepository.findByLogin(request.getPhone())
                .orElseThrow( () -> new UsernameNotFoundException("User Not Found with Email: " + request.getPhone()) );
        Boolean isActive = user.getIsActive();
        if (isActive == null || !isActive) {
            return new ErrorResponse(ResponseKeys.ACCOUNT_NOT_ACTIVE);
        }
//        user.setIsActive(true);
        if (user.getBan()) {
            return new ErrorResponse(ResponseKeys.ACCOUNT_IS_BAN);
        }
        user.setDeviceToken(request.getDeviceToken());
        user.setStayLoggedIn(request.getStayLoggedIn());
        userRepository.save(user);

        Set<String> userRoles = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());

        Set<String> requestRoles = request.getRoles();

        if (Collections.disjoint(userRoles, requestRoles)) {
            return new ErrorResponse(ResponseKeys.ACCOUNT_IS_BAN);
        }

        System.out.println("User Id: " +user.getId());
        var jwtToken = jwtService.generateToken(user);
        System.out.println("jwtToken: "+jwtToken);
        var refreshToken = jwtService.generateRefreshToken(user);
        System.out.println("refreshToken: "+refreshToken);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return new SuccessResponse(AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build());
    }


    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .operation("LOGIN")
                .createdAt(DateUtils.nowInLibya())
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public AuthenticationResponse refreshToken(HttpServletRequest request) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return null ;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractPhone(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByLogin(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                AuthenticationResponse authenticationResponse = new AuthenticationResponse();
                authenticationResponse.setRefreshToken(refreshToken);
                authenticationResponse.setAccessToken(accessToken);
                return AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();

            }
        }
        return null ;
    }

    public ResponseEntity<?> getUserInfo(UserDetails userDetails) {
        User user = this.userRepository.findByLogin(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return ResponseEntity.ok(new SuccessResponse<>(
                LoginInfoResponse.builder()
                        .user(userMapper.userToUserDto(user))
                        .build()));
    }

}
