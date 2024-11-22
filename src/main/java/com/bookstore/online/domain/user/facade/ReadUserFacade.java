package com.bookstore.online.domain.user.facade;

import com.bookstore.online.domain.user.dto.request.ReqSigninDto;
import com.bookstore.online.domain.user.entity.UserEntity;
import com.bookstore.online.domain.user.service.ReadUserService;
import com.bookstore.online.global.exception.SigninException;
import com.bookstore.online.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
@RequiredArgsConstructor
public class ReadUserFacade {

  private final ReadUserService readUserService;
  private final JwtProvider jwtProvider;
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public String signin(ReqSigninDto dto, BindingResult bindingResult) {
    UserEntity user = readUserService.findUserByUserId(dto.userId());

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
