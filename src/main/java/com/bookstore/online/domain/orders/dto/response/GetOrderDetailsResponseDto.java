package com.bookstore.online.domain.orders.dto.response;

import com.bookstore.online.domain.orders.dto.request.GetOrderDetailsDTO;
import com.bookstore.online.global.dto.ResponseCode;
import com.bookstore.online.global.dto.ResponseDto;
import com.bookstore.online.global.dto.ResponseMessage;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GetOrderDetailsResponseDto extends ResponseDto {

  Integer orderNumber; // 주문번호
  String userId; // 고객 아이디
  String name; // 고객 이름
  String orderDate; // 주문날짜
  Integer totalPrice; // 총가격
  String status; // 결제상태

  public GetOrderDetailsResponseDto (GetOrderDetailsDTO dto) {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
     this.orderNumber = dto.getOrderNumber();
     this.userId = dto.getUserId();
     this.name = dto.getName();
     this.orderDate = dto.getOrderDate();
     this.totalPrice = dto.getTotalPrice();
     this.status = dto.getStatus();
  }

  public static ResponseEntity<GetOrderDetailsResponseDto> success(GetOrderDetailsDTO dto) {
    GetOrderDetailsResponseDto responseBody = new GetOrderDetailsResponseDto(dto);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
