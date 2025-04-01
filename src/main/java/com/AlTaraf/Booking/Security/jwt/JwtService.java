package com.AlTaraf.Booking.Security.jwt;


import com.AlTaraf.Booking.database.entity.ERole;
import com.AlTaraf.Booking.database.entity.Role;
import com.AlTaraf.Booking.database.entity.User;
import com.AlTaraf.Booking.database.repository.UserRepository;
import com.AlTaraf.Booking.service.UserRolesService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.function.Function;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtService {

//  @Value("${application.security.jwt.secret-key}")
//  private String secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256).toString();

  @Value("${bezkoder.app.jwtSecret}")
  private String secretKey; // Correctly injected from properties

  @Value("${bezkoder.app.jwtExpirationMs}")
  private long jwtExpiration;

  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshExpiration;

  @Value("${bezkoder.app.stayLoggedInExpirationMs}")
  private long stayLoggedInExpirationMs;

  private final UserRolesService userRoleService;

  private final UserRepository userRepository;

  public String extractPhone(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(UserDetails userDetails) {
    System.out.println("***********************************");
    System.out.println("Before generateToken");
    User user = userRepository.findByLogin(userDetails.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    System.out.println("user.getPhone(): " + user.getPhone());
    List<Role> roleList = userRoleService.getRoleByUserId(user.getId());
    System.out.println("role: " + roleList.get(0));
    List<ERole> roles = roleList.stream().map(Role::getName).toList();
    System.out.println("ERole: " + roles.get(0));
    Map<String, Object> claims = new HashMap<>();
    claims.put("roles", roles);
    System.out.println("After generateToken");
    System.out.println("***********************************");
    return generateToken(claims, userDetails);
  }

  public String generateToken(
          Map<String, Object> extraClaims,
          UserDetails userDetails
  ) {
    return buildToken(extraClaims, userDetails, jwtExpiration);
  }

  public String generateRefreshToken(
          UserDetails userDetails
  ) {
    return buildToken(new HashMap<>(), userDetails, refreshExpiration);
  }

  private String buildToken(
          Map<String, Object> extraClaims,
          UserDetails userDetails,
          long expiration
  ) {
    User user = userRepository.findByLogin(userDetails.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    System.out.println("********************** buildToken ********************");
    Boolean stayLoggedIn = user.getStayLoggedIn();
    System.out.println("stayLoggedIn: " + stayLoggedIn);
    System.out.println("expiration: " + expiration);
    System.out.println("stayLoggedInExpirationMs: " + stayLoggedInExpirationMs);

    long expirationMs = stayLoggedIn ? stayLoggedInExpirationMs : expiration;
    System.out.println("expirationMs: " + expirationMs);

    return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
            .signWith(key(), SignatureAlgorithm.HS256)
            .compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractPhone(token);
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
            .setSigningKey(key())
            .build()
            .parseClaimsJws(token)
            .getBody();
  }

  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
  }
}
