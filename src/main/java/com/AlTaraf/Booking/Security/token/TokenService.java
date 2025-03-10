package com.AlTaraf.Booking.Security.token;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TokenService {

    private TokenRepository tokenRepository;

    public Page<Token> findAllByUser(Integer userId, Integer page, Integer size) {
        return tokenRepository.findAllByUser(userId, PageRequest.of(page, size , Sort.by("id").descending()));
    }

    @Transactional
    public void logoutUser(Integer userId){
    tokenRepository.revokeTokenByUser(userId , "LOGOUT");

    }
}
