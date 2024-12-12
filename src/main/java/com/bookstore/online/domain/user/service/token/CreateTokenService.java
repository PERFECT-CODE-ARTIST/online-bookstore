package com.bookstore.online.domain.user.service.token;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTokenService {

  private final RedisTemplate<String, Object> redisTemplate;

  public void cacheAccessToken(String token, String userId) {
    redisTemplate.opsForValue().set("token:" + token, userId, 1, TimeUnit.DAYS);
  }

}
