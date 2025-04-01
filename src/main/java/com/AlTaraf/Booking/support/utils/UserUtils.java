package com.AlTaraf.Booking.support.utils;

import com.AlTaraf.Booking.database.entity.User;
import com.AlTaraf.Booking.database.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class UserUtils {

    @Autowired
    private UserRepository userRepository;


    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            user = userRepository.findByLogin(currentUserName).get();
        }
        return user;
    }

}
