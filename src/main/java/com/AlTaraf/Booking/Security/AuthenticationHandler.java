package com.AlTaraf.Booking.Security;


import com.AlTaraf.Booking.Security.jwt.JwtService;
import com.AlTaraf.Booking.Security.token.Token;
import com.AlTaraf.Booking.Security.token.TokenRepository;
import com.AlTaraf.Booking.Security.token.TokenType;
import com.AlTaraf.Booking.database.entity.User;
import com.AlTaraf.Booking.database.repository.UserRepository;
import com.AlTaraf.Booking.support.utils.DateUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationHandler {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse mobileAuthenticate(AuthenticationRequest request) {
        // add in login auth mac address to mach with linked user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getPhone(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByLogin(request.getPhone())
                .orElseThrow( () -> new UsernameNotFoundException("User Not Found with Email: " + request.getPhone()) );
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
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

//    public ResponseEntity<?> getUserInfo(UserDetails userDetails) {
//        SecurityUser securityUser = this.repository.findByLogin(userDetails.getUsername())
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        EmployeeDto employeeDto = null;
//        CustomerDto customerDto = null;
//        if (securityUser.getEmployee() != null) {
//            employeeDto = employeeMapper.toBaseDto(securityUser.getEmployee());
//        }
//        if (securityUser.getCustomer() != null) {
//            customerDto = customersMapper.toBaseDto(securityUser.getCustomer());
//        }
//        return ResponseEntity.ok(new SuccessResponse<>(
//                LoginInfoResponse.builder()
//                        .employee(employeeDto)
//                        .customer(customerDto)
//                        .securityUser(securityUserMapper.toBaseDto(securityUser))
//                        .build()));
//    }

}
