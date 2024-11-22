package com.bookstore.online.domain.orders.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class GetOrderDetailsDTO {

  Integer orderNumber; // 주문번호
  String userId; // 고객 아이디
  String name; // 고객 이름
  String orderDate; // 주문날짜
  Integer totalPrice; // 총가격
  String status; // 결제상태

}
