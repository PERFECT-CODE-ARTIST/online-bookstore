package com.bookstore.online.domain.user.service.user;

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
  private final UserRepository userRepository;
  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public void saveUser(ReqSignupDto dto) {
    userRepository.save(dto.toEntity(passwordEncoder));
  }

}