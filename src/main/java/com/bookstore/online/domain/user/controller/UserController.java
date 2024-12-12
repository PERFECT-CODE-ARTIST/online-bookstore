package com.bookstore.online.domain.user.controller;

import com.bookstore.online.domain.user.dto.request.ReqSigninDto;
import com.bookstore.online.domain.user.dto.request.ReqSignupDto;
import com.bookstore.online.domain.user.facade.UserFacade;
import jakarta.validation.Valid;
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

  private final UserFacade userFacade;

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody @Valid ReqSignupDto dto,
      BindingResult bindingResult) {
    return ResponseEntity.ok().body(userFacade.signup(dto, bindingResult));
  }

  @PostMapping("/signin")
  public ResponseEntity<?> signin(@RequestBody @Valid ReqSigninDto dto,
      BindingResult bindingResult) {
    return ResponseEntity.ok().body(userFacade.signin(dto, bindingResult));
  }

}