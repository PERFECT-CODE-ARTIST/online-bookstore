package com.bookstore.online.domain.user.service;

import com.bookstore.online.domain.user.dto.request.ReqSignupDto;
import com.bookstore.online.domain.user.entity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserService {

  private final RedisTemplate<String, Object> redisTemplate;
  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public void cacheUser(String key, Object data) {
    redisTemplate.opsForValue().set("user:" + key, data);
  }
}