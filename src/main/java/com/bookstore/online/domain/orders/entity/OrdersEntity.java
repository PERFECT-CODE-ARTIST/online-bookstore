package com.bookstore.online.domain.orders.entity;

import com.bookstore.online.domain.orders.dto.request.BeforePaymentRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Orders")
@Table(name = "orders")
public class OrdersEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer orderNumber; // 주문고유번호
  private String userId; // 고객Id
  private String orderDate; // 주문 날짜
  private Integer totalPrice; // 총 가격
  @Column(nullable = false)
  private String status; // 결제상태

  public OrdersEntity (BeforePaymentRequestDto dto) {
    this.userId = dto.getUserId();
    this.orderDate = LocalDate.now().toString();
    this.totalPrice = dto.getTotalPrice();
  }

  @PrePersist
  public void proPersist(){
    if(this.status == null) {
      this.status = "결제대기";
    }
  }
}
