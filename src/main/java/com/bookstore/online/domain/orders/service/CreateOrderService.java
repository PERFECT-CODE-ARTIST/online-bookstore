package com.bookstore.online.domain.orders.service;

import com.bookstore.online.domain.orders.entity.OrdersEntity;
import com.bookstore.online.domain.orders.entity.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateOrderService {

  private final OrdersRepository ordersRepository;

  public void beforePayment(OrdersEntity ordersEntity) {
    ordersRepository.save(ordersEntity);
  }
}
