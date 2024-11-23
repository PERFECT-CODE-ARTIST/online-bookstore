package com.bookstore.online.domain.orders.facade;

import com.bookstore.online.domain.book.entity.BooksEntity;
import com.bookstore.online.domain.book.service.ReadBookService;
import com.bookstore.online.domain.book.service.UpdateBookService;
import com.bookstore.online.domain.orders.dto.request.AfterPaymentRequestDto;
import com.bookstore.online.domain.orders.dto.request.BeforePaymentRequestDto;
import com.bookstore.online.domain.orders.dto.request.BookInformationRequestDto;
import com.bookstore.online.domain.orders.dto.result.GetOrderDetailsDTO;
import com.bookstore.online.domain.orders.dto.response.GetOrderDetailsResponseDto;
import com.bookstore.online.domain.orders.entity.OrdersEntity;
import com.bookstore.online.domain.orders.entity.repository.OrdersRepository;
import com.bookstore.online.domain.orders.service.CreateOrderService;
import com.bookstore.online.domain.orders.service.ReadOrderService;
import com.bookstore.online.domain.orders.service.UpdateOrderService;
import com.bookstore.online.global.dto.ResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OrdersFacade {

  private final OrdersRepository ordersRepository;

  private final CreateOrderService createOrderService;
  private final ReadOrderService readOrderService;
  private final UpdateOrderService updateOrderService;

  private final ReadBookService readBookService;
  private final UpdateBookService updateBookService;

  public ResponseEntity<ResponseDto> beforePayment(BeforePaymentRequestDto dto) {
    try {
      OrdersEntity ordersEntity = new OrdersEntity(dto);
      createOrderService.beforePayment(ordersEntity);

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ResponseDto.success();
  }

  public ResponseEntity<? super GetOrderDetailsResponseDto> orderDetails(Integer orderNumber) {

    GetOrderDetailsDTO isOrderNumber;

    try {
      isOrderNumber = readOrderService.orderDetails(orderNumber);
      if (isOrderNumber == null) {
        return ResponseDto.noExistOrderCode();
      }

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetOrderDetailsResponseDto.success(isOrderNumber);
  }

  public ResponseEntity<ResponseDto> afterPayment(Integer orderNumber, AfterPaymentRequestDto dto) {
    try {
      OrdersEntity ordersEntity = readOrderService.findByOrderNumber(orderNumber);
      if (ordersEntity == null)
        return ResponseDto.noExistOrderCode();

      Integer totalPrice = ordersEntity.getTotalPrice();
      Integer isTotalPrice = dto.getTotalPrice();

      System.out.println(totalPrice + "@@@@@@@@@@@@@@@@@@@@" + isTotalPrice);
      if (!totalPrice.equals(isTotalPrice))
        return ResponseDto.noExistOrderPrice();

      ordersEntity.setTotalPrice(isTotalPrice);

      String status = ordersEntity.getStatus();

      if ("결제대기".equals(status)) {
        ordersEntity.setStatus("결제완료");
        updateOrderService.updateOrders(ordersEntity);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success();
  }

  @Transactional
  public ResponseEntity<ResponseDto> bookInformation(BookInformationRequestDto dto) {

    Integer orderNumber = dto.getOrderNumber();
    Integer bookNumber = dto.getBookNumber();
    Integer quantity = dto.getQuantity();
    Integer pricePerUnit = dto.getPricePerUnit();

    try {
      OrdersEntity ordersEntity = readOrderService.findByOrderNumber(orderNumber);
      if (ordersEntity == null)
        return ResponseDto.noExistOrderCode();

      String status = ordersEntity.getStatus();
      if (!status.equals("결제대기"))
        return ResponseDto.noExistOrderCode();

      BooksEntity booksEntity = readBookService.findBookNumber(bookNumber);
      if (booksEntity == null)
        return ResponseDto.noExistBookId();

      Integer totalCount = booksEntity.getBookCount();

      if (totalCount.compareTo(quantity) < 0)
        return ResponseDto.lackOfQuantity();

      Integer subtractValue = totalCount - quantity;

      booksEntity.setBookCount(subtractValue);
      updateBookService.updateBook(booksEntity);

      Integer totalPrice = quantity * pricePerUnit;
      ordersEntity.setTotalPrice(totalPrice);
      updateOrderService.updateOrders(ordersEntity);

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success();
  }

  // 매일 자정마다 실행되어 주문 상태를 자동으로 변경
  @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
  public void updateOrderStatuses() {
    List<OrdersEntity> orders = ordersRepository.findAll();
    for (OrdersEntity order : orders) {
      int daysSinceOrder = getDaysSinceOrder(order.getOrderDate());
      if (daysSinceOrder == 1) {
        // 1일차: 배송 중으로 상태 업데이트
        updateOrderService.updateOrderStatus(order.getOrderNumber(), "배송 중");
      } else if (daysSinceOrder == 2) {
        // 2일차: 발송 완료로 상태 업데이트
        updateOrderService.updateOrderStatus(order.getOrderNumber(), "발송 완료");
      }
    }
  }

  // 주문 상태 수동 변경
  public void updateOrderStatusManually(int orderNumber, String status) {
    updateOrderService.updateOrderStatus(orderNumber, status);
  }

  // 주문 상태 조회
  public String getOrderStatus(int orderNumber) {
    OrdersEntity ordersEntity = ordersRepository.findById(orderNumber)
        .orElseThrow(() -> new RuntimeException("Order not found"));
    return ordersEntity.getStatus();
  }

  private int getDaysSinceOrder(String orderDate) {
    // orderDate를 날짜로 변환 후 현재 날짜와 비교하여 몇 일이 지났는지 계산
    // 여기서는 임시로 1일차로 반환하는 코드로 대체
    return 1; // 임시로 1일차
  }

}
