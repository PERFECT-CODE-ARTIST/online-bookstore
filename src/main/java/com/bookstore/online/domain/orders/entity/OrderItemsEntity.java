package com.bookstore.online.domain.orders.entity;

import com.bookstore.online.domain.orders.dto.request.BookInformationRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "OrderItems")
@Table(name = "order_items")
public class OrderItemsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer orderItemNumber; // 주문상세고유번호
  Integer orderNumber; // 주문번호
  Integer bookNumber; // 책 고유번호
  Integer quantity; // 주문 수량
  Integer pricePerUnit; // 책의 가격

  public OrderItemsEntity(BookInformationRequestDto dto) {
    this.orderNumber = dto.getOrderNumber();
    this.bookNumber = dto.getBookNumber();
    this.quantity = dto.getQuantity();
    this.pricePerUnit = dto.getPricePerUnit();
  }
}
