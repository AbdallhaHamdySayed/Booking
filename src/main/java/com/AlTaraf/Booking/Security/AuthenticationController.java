package com.AlTaraf.Booking.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationHandler handler;
  private final Environment env;

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticateMobile(@RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(handler.mobileAuthenticate(request));
  }

}
