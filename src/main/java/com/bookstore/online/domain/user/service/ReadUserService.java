package com.bookstore.online.domain.user.service;

import com.bookstore.online.domain.user.entity.UserEntity;
import com.bookstore.online.domain.user.entity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadUserService {

    private final UserRepository userRepository;

    public UserEntity findUserByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }
}
