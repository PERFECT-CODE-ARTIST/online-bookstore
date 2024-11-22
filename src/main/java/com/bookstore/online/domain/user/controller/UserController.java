package com.bookstore.online.domain.user.controller;

import com.bookstore.online.domain.user.dto.request.ReqSigninDto;
import com.bookstore.online.domain.user.dto.request.ReqSignupDto;
import com.bookstore.online.domain.user.facade.CreateUserFacade;
import com.bookstore.online.domain.user.facade.ReadUserFacade;
import com.bookstore.online.domain.user.service.ReadUserService;
import jakarta.validation.Valid;
import javax.naming.Binding;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

  private final CreateUserFacade createUserFacade;
  private final ReadUserFacade readUserFacade;

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody @Valid ReqSignupDto dto,
      BindingResult bindingResult) {
    return ResponseEntity.ok().body(createUserFacade.signup(dto, bindingResult));
  }

  @PostMapping("/signin")
  public ResponseEntity<?> signin(@RequestBody @Valid ReqSigninDto dto,
      BindingResult bindingResult) {
    return ResponseEntity.ok().body(readUserFacade.signin(dto, bindingResult));
  }

}