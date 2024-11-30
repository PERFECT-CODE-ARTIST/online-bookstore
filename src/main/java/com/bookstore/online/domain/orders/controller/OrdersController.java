package com.bookstore.online.domain.orders.controller;

import com.bookstore.online.domain.orders.dto.request.AfterPaymentRequestDto;
import com.bookstore.online.domain.orders.dto.request.BeforePaymentRequestDto;
import com.bookstore.online.domain.orders.dto.request.BookInformationRequestDto;
import com.bookstore.online.domain.orders.dto.response.GetOrderDetailsResponseDto;
import com.bookstore.online.domain.orders.facade.OrdersFacade;
import com.bookstore.online.global.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

  // 주문에 포함된 책상세 정보
  @PostMapping("/bookInformation")
  public ResponseEntity<ResponseDto> bookInformation(
    @RequestBody @Valid BookInformationRequestDto requestBody
  ){
    ResponseEntity<ResponseDto> response = ordersFacade.bookInformation(requestBody);
    return response;
  }

  // 결제api(결제완료)
  @PatchMapping("/afterPayment/{orderNumber}")
  public ResponseEntity<ResponseDto> afterPayment(
      @PathVariable("orderNumber") Integer orderNumber,
      @RequestBody @Valid AfterPaymentRequestDto requestBody
  ){
    ResponseEntity<ResponseDto> response = ordersFacade.afterPayment(orderNumber,requestBody);
    return response;
  }

  // 주문내역보기[배송현황도 같이 볼 수 있음]
  @GetMapping("/orderDetails/{orderNumber}")
  public ResponseEntity<? super GetOrderDetailsResponseDto> orderDetails(
      @PathVariable("orderNumber") Integer orderNumber
  ) {
    ResponseEntity<? super GetOrderDetailsResponseDto> response = ordersFacade.orderDetails(orderNumber);
    return  response;
  }

  // 주문 내역 삭제하기(결제대기일 경우만 가능)
  @DeleteMapping("/{orderNumber}")
  public ResponseEntity<ResponseDto> deleteOrders(
    @PathVariable("orderNumber") Integer orderNumber
  ){
    ResponseEntity<ResponseDto> response = ordersFacade.deleteOrders(orderNumber);
    return response;
  }
}
