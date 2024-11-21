package com.bookstore.online.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ReqSigninDto (
    @NotBlank String userId,
    @NotBlank String password) {

}
