package com.AlTaraf.Booking.Security;


import com.AlTaraf.Booking.Security.jwt.AuthEntryPointJwt;
import com.AlTaraf.Booking.Security.jwt.AuthTokenFilter;
import com.AlTaraf.Booking.Security.service.UserDetailsServiceImpl;
import com.AlTaraf.Booking.Service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    @Lazy
    private UserService userService; // Ensure this is autowired


    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // Disabling CSRF
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests((authz) -> {
                    authz.requestMatchers("api/users/login").permitAll()
                            .requestMatchers("api/users/register").permitAll()
                            .requestMatchers("api/users/send-otp").permitAll()
                            .requestMatchers("api/users/send-otp-whats").permitAll()
                            .requestMatchers("api/users/validate-otp").permitAll()
                            .requestMatchers("api/users/forget-password/{phone}").permitAll()
                            .requestMatchers("api/cities/all").permitAll()
                            .requestMatchers("api/roles/all").permitAll()
                            .requestMatchers("/swagger-ui/**").permitAll()
                            .requestMatchers("/bezkoder-api-docs/**").permitAll()
                            .requestMatchers("/files-for-unit/**").permitAll()
                            .requestMatchers("/file-for-profile/**").permitAll()
                            .requestMatchers("/file-for-pdf/**").permitAll()
                            .requestMatchers("/file-for-ads/**").permitAll()
//

                            .requestMatchers("/api/ads/package-ads").permitAll()
//

                            .requestMatchers("/api/transactions/total").permitAll()
                            .requestMatchers("/api/transactions/details").permitAll()

                            .requestMatchers("/api/users/**").permitAll()
                            .requestMatchers("/api/admin/**").permitAll()
                            .requestMatchers("/WhatsApp/**").permitAll()
                            .requestMatchers("/Notification/**").permitAll()
                            .requestMatchers("/Api/Units/By-Id-General/**").permitAll()
                            .requestMatchers("/api/admin/change/status/units/**").permitAll()
                            .requestMatchers("/api/cities/**").permitAll()
                            .requestMatchers("/payment/back-end-url").permitAll()
                            .requestMatchers("/ws-payment/**").permitAll()
                            .requestMatchers("/test.html").permitAll()



                            .anyRequest().authenticated();
                });

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();


    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }


}