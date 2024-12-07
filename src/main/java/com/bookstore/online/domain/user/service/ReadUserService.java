package com.bookstore.online.domain.user.service;

import com.bookstore.online.domain.user.entity.UserEntity;
import com.bookstore.online.domain.user.entity.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadUserService {

  private final RedisTemplate<String, Object> redisTemplate;
  private final ObjectMapper objectMapper;

  public <T> Object readCacheUser(String key, Class<T> type) {
    Object data = redisTemplate.opsForValue().get("user:" + key);
    return objectMapper.convertValue(data, type);
  }
}
