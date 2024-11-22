package com.bookstore.online.domain.orders.service;

import com.bookstore.online.domain.orders.dto.result.GetOrderDetailsDTO;
import com.bookstore.online.domain.orders.entity.OrdersEntity;
import com.bookstore.online.domain.orders.entity.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadOrderService {

  private final OrdersRepository ordersRepository;

  public GetOrderDetailsDTO orderDetails(Integer orderNumber) {
    return ordersRepository.orderDetails(orderNumber);
  }

  public OrdersEntity findByOrderNumber(Integer orderNumber) {
    return ordersRepository.findByOrderNumber(orderNumber);
  }
}
