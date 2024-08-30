package com.AlTaraf.Booking.Config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {

 @Override
 public Optional<String> getCurrentAuditor() {
  if (SecurityContextHolder.getContext().getAuthentication() == null || SecurityContextHolder.getContext().getAuthentication().getName() == null) {
   return Optional.of("anonymousUser");
  } else {
   return Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
  }

 }
}

// ----------------- Use below code for spring security -----------------------

