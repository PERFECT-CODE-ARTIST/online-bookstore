package com.bookstore.online.domain.orders.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AfterPaymentRequestDto {

  Integer totalPrice;
}
