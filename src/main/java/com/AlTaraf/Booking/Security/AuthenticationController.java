package com.AlTaraf.Booking.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationHandler handler;

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticateMobile(@RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(handler.mobileAuthenticate(request));
  }

  @GetMapping(value = "/login-info")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
    return handler.getUserInfo(userDetails);
  }


}
