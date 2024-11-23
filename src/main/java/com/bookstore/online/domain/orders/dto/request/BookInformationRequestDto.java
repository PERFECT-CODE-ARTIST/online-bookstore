package com.bookstore.online.domain.orders.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookInformationRequestDto {

  Integer orderNumber; // 주문번호
  Integer bookNumber; // 책번호
  Integer quantity; // 수량
  Integer pricePerUnit; // 하나당가격

}
