package com.bookstore.online.domain.orders.controller;

import com.bookstore.online.domain.orders.dto.request.BeforePaymentRequestDto;
import com.bookstore.online.domain.orders.dto.response.GetOrderDetailsResponseDto;
import com.bookstore.online.domain.orders.facade.OrdersFacade;
import com.bookstore.online.global.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {

  private final OrdersFacade ordersFacade;

  // 주문하기(결제대기)
  @PostMapping("/beforePayment")
  public ResponseEntity<ResponseDto> beforePayment(
    @RequestBody @Valid BeforePaymentRequestDto requestBody
  ){
    ResponseEntity<ResponseDto> response = ordersFacade.beforePayment(requestBody);
    return response;
  }

  // 주문내역보기
  @GetMapping("/orderDetails/{orderNumber}")
  public ResponseEntity<? super GetOrderDetailsResponseDto> orderDetails(
    @PathVariable("orderNumber") Integer orderNumber
  ) {
    ResponseEntity<? super GetOrderDetailsResponseDto> response = ordersFacade.orderDetails(orderNumber);
    return  response;
    // d
  }
}
