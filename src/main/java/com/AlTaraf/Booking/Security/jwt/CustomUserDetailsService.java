//package com.AlTaraf.Booking.Security.jwt;
//
//import com.AlTaraf.Booking.database.entity.User;
//import com.AlTaraf.Booking.database.repository.UserRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@Transactional
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class); // ✅ Manually define logger
//
//
//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
//        log.info("Attempting to load user by phone: {}", phone);  // ✅ Log input
//
//        return userRepository.findByPhone(phone)
//                .orElseThrow(() -> {
//                    log.error("User not found with phone: {}", phone);  // ✅ Log error
//                    return new UsernameNotFoundException("User Not Found with phone: " + phone);
//                });
//    }
//
//}