package com.bookstore.online.domain.orders.service;

import com.bookstore.online.domain.orders.entity.OrdersEntity;
import com.bookstore.online.domain.orders.entity.repository.OrdersRepository;
import com.bookstore.online.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteOrderService {

  private final OrdersRepository ordersRepository;

  public void deleteOrders(OrdersEntity ordersEntity) {
    ordersRepository.delete(ordersEntity);
  }
}
