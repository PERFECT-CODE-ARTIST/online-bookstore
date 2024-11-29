package com.bookstore.online.domain.orders.facade;

import com.bookstore.online.domain.book.entity.BooksEntity;
import com.bookstore.online.domain.book.service.ReadBookService;
import com.bookstore.online.domain.book.service.UpdateBookService;
import com.bookstore.online.domain.orders.dto.request.AfterPaymentRequestDto;
import com.bookstore.online.domain.orders.dto.request.BeforePaymentRequestDto;
import com.bookstore.online.domain.orders.dto.request.BookInformationRequestDto;
import com.bookstore.online.domain.orders.entity.result.GetOrderDetailsResultSet;
import com.bookstore.online.domain.orders.dto.response.GetOrderDetailsResponseDto;
import com.bookstore.online.domain.orders.entity.OrderItemsEntity;
import com.bookstore.online.domain.orders.entity.OrdersEntity;
import com.bookstore.online.domain.orders.service.CreateOrderService;
import com.bookstore.online.domain.orders.service.DeleteOrderService;
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
@Transactional
@RequiredArgsConstructor
public class OrdersFacade {

  private final CreateOrderService createOrderService;
  private final ReadOrderService readOrderService;
  private final UpdateOrderService updateOrderService;
  private final DeleteOrderService deleteOrderService;

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

    GetOrderDetailsResultSet resultSet;

    try {
      resultSet = readOrderService.orderDetails(orderNumber);

      if(resultSet == null) {
        return ResponseDto.noExistOrderCode();
      }

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetOrderDetailsResponseDto.success(resultSet);
  }

  public ResponseEntity<ResponseDto> afterPayment(Integer orderNumber, AfterPaymentRequestDto dto) {
    try {
      OrdersEntity ordersEntity = readOrderService.findByOrderNumber(orderNumber);
      if (ordersEntity == null)
        return ResponseDto.noExistOrderCode();

      Integer totalPrice = ordersEntity.getTotalPrice();
      Integer isTotalPrice = dto.getTotalPrice();

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

  public ResponseEntity<ResponseDto> bookInformation(BookInformationRequestDto dto) {

    Integer orderNumber = dto.getOrderNumber();
    Integer bookNumber = dto.getBookNumber();
    Integer quantity = dto.getQuantity();
    Integer pricePerUnit = dto.getPricePerUnit();

    try {

      boolean isMatched = readOrderService.existsByOrderNumberAndBookNumber(orderNumber,bookNumber);
      if(isMatched) return ResponseDto.duplicatedNumber();

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

      OrderItemsEntity orderItemsEntity = new OrderItemsEntity(dto);
      createOrderService.bookInformation(orderItemsEntity);

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success();
  }

  // @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
  @Scheduled(cron = "*/10 * * * * ?") // 10초마다 실행 (test 하기위해)
  public void updateOrderStatuses() {
    try {
      List<OrdersEntity> orders = readOrderService.findAll();

      for (OrdersEntity ordersEntity : orders) {
        Integer orderNumber = ordersEntity.getOrderNumber();
        String currentStatus = ordersEntity.getStatus();


        if ("결제완료".equals(currentStatus)) {
          updateOrderService.updateOrderStatus(orderNumber, "배송중");
          System.out.println("결제완료 상태를 배송중으로 변경하였습니다: " + orderNumber);
        } else if ("배송중".equals(currentStatus)) {
          updateOrderService.updateOrderStatus(orderNumber, "배송완료");
          System.out.println("배송중 상태를 배송완료로 변경하였습니다: " + orderNumber);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public ResponseEntity<ResponseDto> deleteOrders(Integer orderNumber) {

    try {
      OrdersEntity ordersEntity = readOrderService.findByOrderNumber(orderNumber);
      if(ordersEntity == null) {
        return ResponseDto.noExistOrderCode();
      }
      String status = ordersEntity.getStatus();
      if ("결제대기".equals(status)){
        deleteOrderService.deleteOrders(ordersEntity);
      }else {
        return ResponseDto.noExistOrderCode();
      }

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success();
  }

}
