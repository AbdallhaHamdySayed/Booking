package com.AlTaraf.Booking.Security.service;

import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrPhone) throws UsernameNotFoundException {
            // If it's a valid phone number, try to find the user by phone
            User user = userRepository.findByPhone(usernameOrPhone)
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found with phone number: " + usernameOrPhone));

            return UserDetailsImpl.build(user);
    }

}