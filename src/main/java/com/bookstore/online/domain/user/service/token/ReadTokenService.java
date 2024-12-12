package com.bookstore.online.domain.user.service.token;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadTokenService {

  private final RedisTemplate<String, Object> redisTemplate;

  public String readCacheUser(String token) {
    return (String) redisTemplate.opsForValue().get("token:" + token);
  }

}
