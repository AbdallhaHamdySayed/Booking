package com.AlTaraf.Booking.Security.jwt;

import com.AlTaraf.Booking.Security.token.TokenRepository;
import com.AlTaraf.Booking.response.keys.ErrorStatus;
import com.AlTaraf.Booking.response.keys.ResponseKeys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  @Autowired
  private JwtService jwtService;
  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private TokenRepository tokenRepository;

  @Autowired
  private InvalidJWTToken invalidJWTToken;

  @Qualifier("handlerExceptionResolver")
  private HandlerExceptionResolver exceptionResolver;


  @Autowired
  public JwtAuthenticationFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
    this.exceptionResolver = exceptionResolver;
  }

  @Override
  protected void doFilterInternal( @NonNull HttpServletRequest request,
                                   @NonNull HttpServletResponse response,
                                   @NonNull FilterChain filterChain ) throws ServletException, IOException {
    if (request.getServletPath().contains("/auth/authenticate")) {
      filterChain.doFilter(request, response);
      return;
    }
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String userPhone ;
    try {
      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    jwt = authHeader.substring(7);

      userPhone  = jwtService.extractPhone(jwt);

      System.out.println("**************************************************************************************");
      System.out.println("userPhone: " + userPhone);
    if (userPhone  != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userPhone );
      System.out.println("UserDetails loaded: " + userDetails.getUsername());

      var isTokenValid = tokenRepository.findByToken(jwt)
          .map(t -> !t.isExpired() && !t.isRevoked())
          .orElse(false);
      System.out.println("Token found in DB: " + tokenRepository.findByToken(jwt).isPresent());
      System.out.println("Token valid: " + isTokenValid);
      System.out.println("JWT validation result: " + jwtService.isTokenValid(jwt, userDetails));
      if (isTokenValid.booleanValue()) {
        System.out.println("isTokenValid Yeh: ");
      } else {
        invalidJWTToken.sendErrorResponse(response, ErrorStatus.valueOf(ResponseKeys.EXPIRED_JWT_EXCEPTION));

        System.out.println("isTokenValid Not: ");
        return;
      }
      if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            userDetails.getPassword(),
            userDetails.getAuthorities()
        );
        authToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
    filterChain.doFilter(request, response);
    } catch (Exception ex) {
      System.out.println("****************************************************");
      System.out.println("doFilterInternal Exception: " + ex.getMessage());
      System.out.println("****************************************************");
      exceptionResolver.resolveException(request, response, null, ex);
    }
  }
}
