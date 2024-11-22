package com.bookstore.online.domain.orders.facade;

import com.bookstore.online.domain.orders.dto.request.BeforePaymentRequestDto;
import com.bookstore.online.domain.orders.dto.request.GetOrderDetailsDTO;
import com.bookstore.online.domain.orders.dto.response.GetOrderDetailsResponseDto;
import com.bookstore.online.domain.orders.entity.OrdersEntity;
import com.bookstore.online.domain.orders.service.CreateOrderService;
import com.bookstore.online.domain.orders.service.GetOrderService;
import com.bookstore.online.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrdersFacade {

  private final CreateOrderService createOrderService;
  private final GetOrderService getOrderService;

  public ResponseEntity<ResponseDto> beforePayment(BeforePaymentRequestDto dto) {
    try {
      OrdersEntity ordersEntity = new OrdersEntity(dto);
      createOrderService.beforePayment(ordersEntity);

    }catch (Exception e) {
       e.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ResponseDto.success();
  }

  public ResponseEntity<? super GetOrderDetailsResponseDto> orderDetails(Integer orderNumber) {

    GetOrderDetailsDTO isOrderNumber;

    try {
      isOrderNumber = getOrderService.orderDetails(orderNumber);
      if (isOrderNumber == null) {
        return ResponseDto.notOrderNumber();
      }

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetOrderDetailsResponseDto.success(isOrderNumber);
  }

}
