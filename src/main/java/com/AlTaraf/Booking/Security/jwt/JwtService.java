package com.AlTaraf.Booking.Security.jwt;

import com.AlTaraf.Booking.Security.service.UserDetailsImpl;
import com.AlTaraf.Booking.database.entity.Role;
import com.AlTaraf.Booking.database.entity.User;
import com.AlTaraf.Booking.database.repository.UserRepository;
import com.AlTaraf.Booking.service.RoleService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class JwtService {

  @Value("${application.security.jwt.secret-key}")
  private String secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256).toString();
  private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  @Value("${application.security.jwt.expiration}")
  private long jwtExpiration;
  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshExpiration;
  private final UserRepository userRepository;
  private final RoleService roleService;

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(UserDetailsImpl userDetails) {
    User user = userRepository.findByLogin(userDetails.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    Role securityRole =  roleService.getRoleById(user.getRole().getId());
    String role = securityRole.getEnglishName();
    Map<String, Object> claims = new HashMap<>();
    claims.put("roles", role);
    return generateToken(claims, userDetails);
  }

  public String generateToken(
      Map<String, Object> extraClaims,
      UserDetailsImpl userDetails
  ) {
    return buildToken(extraClaims, userDetails, jwtExpiration);
  }

  public String generateRefreshToken(
      UserDetailsImpl userDetails
  ) {
    return buildToken(new HashMap<>(), userDetails, refreshExpiration);
  }

  private String buildToken(
          Map<String, Object> extraClaims,
          UserDetailsImpl userDetails,
          long expiration
  ) {
    return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey() {

    return Keys.hmacShaKeyFor(secretKey.getBytes());
  }
}
