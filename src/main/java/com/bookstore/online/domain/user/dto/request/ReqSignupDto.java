package com.bookstore.online.domain.user.dto.request;

import com.bookstore.online.domain.user.entity.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public record ReqSignupDto(
  @NotBlank
  @Pattern(regexp = "^[a-zA-Z0-9]{6,}$", message = "영문자와 숫자만 입력가능하고, 6자리 이상이어야 합니다.")
  String userId,
  @NotBlank
  @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[a-z\\d@$!%*?&]{8,}$",
      message = "8자리 이상이고 하나 이상의 영소문자, 숫자, 특수문자가 포함되어야 합니다.")
  String password,
  @NotBlank
  String checkPassword,
  @NotBlank
  @Email
  String email,
  @NotBlank
  String name
) {

  public UserEntity toEntity(PasswordEncoder passwordEncoder) {
    return UserEntity.builder()
        .userId(userId)
        .password(passwordEncoder.encode(password))
        .name(name)
        .email(email)
        .build();
  }
}