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

  public void updateOrderStatus(Integer orderNumber, String status) {
    OrdersEntity ordersEntity = ordersRepository.findByOrderNumber(orderNumber);
    ordersEntity.setStatus(status);
    ordersRepository.save(ordersEntity);
  }

}
