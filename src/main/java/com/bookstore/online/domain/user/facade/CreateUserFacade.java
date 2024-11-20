package com.bookstore.online.domain.user.facade;

import com.bookstore.online.domain.user.dto.request.ReqSignupDto;
import com.bookstore.online.domain.user.entity.UserEntity;
import com.bookstore.online.domain.user.service.CreateUserService;
import com.bookstore.online.domain.user.service.ReadUserService;
import com.bookstore.online.global.exception.SignupValidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
@RequiredArgsConstructor
public class CreateUserFacade {

    private final CreateUserService createUserService;
    private final ReadUserService readUserService;

    @Transactional(rollbackFor = Exception.class)
    public void signup(ReqSignupDto dto, BindingResult bindingResult) {
        if (!dto.getPassword().equals(dto.getCheckPassword())) {
            FieldError fieldError = new FieldError(
                    "passwordMismatch",
                    "passwordMismatch",
                    "비밀번호가 일치하지 않습니다.");
            bindingResult.addError(fieldError);
        }

        UserEntity user = readUserService.findUserByUserId(dto.getUserId());
        System.out.println(user);
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
        System.out.println("success");
        createUserService.saveUser(dto);
    }
}