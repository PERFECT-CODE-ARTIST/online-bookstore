package com.bookstore.online.domain.user.service.user;

import com.bookstore.online.domain.user.entity.UserEntity;
import com.bookstore.online.domain.user.entity.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadUserService {

  private final RedisTemplate<String, Object> redisTemplate;
  private final UserRepository userRepository;
  private final ObjectMapper objectMapper;

  public UserEntity findUserByUserId(String userId) {
    return userRepository.findByUserId(userId);
  }
}