package com.bookstore.online.domain.orders.service;

import com.bookstore.online.domain.orders.entity.OrdersEntity;
import com.bookstore.online.domain.orders.entity.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateOrderService {

  private final OrdersRepository ordersRepository;

  public void updateOrders(OrdersEntity ordersEntity) {
    ordersRepository.save(ordersEntity);
  }

  // 주문 상태 변경
  public void updateOrderStatus(int orderNumber, String status) {
    OrdersEntity ordersEntity = ordersRepository.findById(orderNumber)
        .orElseThrow(() -> new RuntimeException("Order not found"));
    ordersEntity.setStatus(status);
    ordersRepository.save(ordersEntity);
  }

}
