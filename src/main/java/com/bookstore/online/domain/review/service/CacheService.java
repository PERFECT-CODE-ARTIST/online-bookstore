package com.bookstore.online.domain.review.service;

// Redis 캐싱 서비스

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CacheService {
  private final RedisTemplate<String, Object> redisTemplate;
  private final ObjectMapper objectMapper;

  // 데이터 캐싱
  public void cacheData(String key, Object data, long timeoutSeconds) {
    try {
      redisTemplate.opsForValue().set(key, data, timeoutSeconds, TimeUnit.SECONDS);
      log.info("data cached successfully for key: {}", key);
    } catch (Exception exception) {
      log.error("Error caching data: {}", exception.getMessage());
      throw new RuntimeException("Cache operation failed", exception);
    }
  }

  // 캐싱된 데이터 조회
  public <T> Optional<T> getCachedData(String key, Class<T> type) {
    try {
      Object data = redisTemplate.opsForValue().get(key);
      if (data == null) return Optional.empty();

      return Optional.of(objectMapper.convertValue(data, type));
    } catch (Exception exception) {
      return Optional.empty();
    }
  }

  // 캐싱된 데이터 삭제
  public void deleteCachedData(String key) {
    try {
      Boolean result = redisTemplate.delete(key);
      if (Boolean.TRUE.equals(result)) log.info("Data deleted successfully for key: {}", key);
      else log.warn("No data found for key: {}", key);
    } catch (Exception exception) {
      log.error("Error delete cached data: {}", exception.getMessage());
      throw new RuntimeException("Delete cached operation failed", exception);
    }
  }

}
