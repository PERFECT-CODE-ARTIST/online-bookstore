package com.bookstore.online.domain.orders.dto.request;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeforePaymentRequestDto {

  @NotBlank
  String userId;
  Integer totalPrice;

}
