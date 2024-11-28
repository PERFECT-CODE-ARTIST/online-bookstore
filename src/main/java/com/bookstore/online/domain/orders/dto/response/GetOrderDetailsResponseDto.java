package com.bookstore.online.domain.orders.dto.response;

import com.bookstore.online.domain.orders.entity.OrdersEntity;
import com.bookstore.online.domain.orders.entity.result.GetOrderDetailsResultSet;
import com.bookstore.online.domain.user.entity.UserEntity;
import com.bookstore.online.global.dto.ResponseCode;
import com.bookstore.online.global.dto.ResponseDto;
import com.bookstore.online.global.dto.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@AllArgsConstructor
public class GetOrderDetailsResponseDto extends ResponseDto {

  Integer orderNumber; // 주문번호
  String userId; // 고객 아이디
  String name; // 고객 이름
  String orderDate; // 주문날짜
  Integer totalPrice; // 총가격
  String status; // 결제상태

  public GetOrderDetailsResponseDto (GetOrderDetailsResultSet resultSet) {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
     this.orderNumber = resultSet.orderNumber();
     this.userId = resultSet.userId();
     this.name = resultSet.name();
     this.orderDate = resultSet.orderDate();
     this.totalPrice = resultSet.totalPrice();
     this.status = resultSet.status();
  }

  public static ResponseEntity<GetOrderDetailsResponseDto> success(GetOrderDetailsResultSet resultSet) {
    GetOrderDetailsResponseDto responseBody = new GetOrderDetailsResponseDto(resultSet);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
