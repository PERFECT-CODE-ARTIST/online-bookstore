package com.bookstore.online.domain.user.facade;

import com.bookstore.online.domain.user.dto.request.ReqSigninDto;
import com.bookstore.online.domain.user.dto.request.ReqSignupDto;
import com.bookstore.online.domain.user.entity.UserEntity;
import com.bookstore.online.domain.user.service.CreateUserService;
import com.bookstore.online.domain.user.service.ReadUserService;
import com.bookstore.online.global.exception.DatabaseException;
import com.bookstore.online.global.exception.SigninException;
import com.bookstore.online.global.exception.SignupValidException;
import com.bookstore.online.global.jwt.JwtProvider;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
@RequiredArgsConstructor
public class UserFacade {

  private final CreateUserService createUserService;
  private final ReadUserService readUserService;
  private final JwtProvider jwtProvider;
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Transactional(rollbackFor = Exception.class)
  public boolean signup(ReqSignupDto dto, BindingResult bindingResult) {
    if (!dto.password().equals(dto.checkPassword())) {
      FieldError fieldError = new FieldError(
          "passwordMismatch",
          "passwordMismatch",
          "비밀번호가 일치하지 않습니다.");
      bindingResult.addError(fieldError);
    }

    UserEntity user = (UserEntity) readUserService.readCacheUser(dto.userId(), UserEntity.class);
    if (user != null) {
      FieldError fieldError = new FieldError(
          "duplicateUser",
          "duplicateUser",
          "사용할 수 없는 아이디입니다."
      );
      bindingResult.addError(fieldError);
    }

    if (bindingResult.hasErrors()) {
      throw new SignupValidException(bindingResult.getFieldErrors());
    }
    try {
      createUserService.cacheUser(dto.userId(), dto.toEntity(passwordEncoder));
    } catch (Exception e) {
      e.printStackTrace();
      throw new DatabaseException("DB 에러");
    }

    return true;
  }

  public String signin(ReqSigninDto dto, BindingResult bindingResult) {
    UserEntity user = (UserEntity) readUserService.readCacheUser(dto.userId(), UserEntity.class);

    if (bindingResult.hasFieldErrors()) {
      throw new SigninException("사용자 정보를 다시 확인하세요");
    }

    if (user == null) {
      throw new SigninException("사용자 정보를 다시 확인하세요");
    }

    if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
      throw new SigninException("사용자 정보를 다시 확인하세요");
    }

    return jwtProvider.generateAccessToken(user.getUserId());
  }
}
