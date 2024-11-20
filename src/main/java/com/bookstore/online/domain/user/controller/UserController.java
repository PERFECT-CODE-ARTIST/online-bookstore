package com.bookstore.online.domain.user.controller;

import com.bookstore.online.domain.user.dto.request.ReqSignupDto;
import com.bookstore.online.domain.user.facade.CreateUserFacade;
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

    private final CreateUserFacade createUserFacade;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid ReqSignupDto dto, BindingResult bindingResult) {
        createUserFacade.signup(dto, bindingResult);
        return ResponseEntity.ok().body("회원가입 성공");
    }

}
