package com.bookstore.online.domain.orders.service;

import com.bookstore.online.domain.orders.entity.result.GetOrderDetailsResultSet;
import com.bookstore.online.domain.orders.entity.OrdersEntity;
import com.bookstore.online.domain.orders.entity.repository.OrderItemsRepository;
import com.bookstore.online.domain.orders.entity.repository.OrdersRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadOrderService {

  private final OrdersRepository ordersRepository;
  private final OrderItemsRepository orderItemsRepository;

  public List<OrdersEntity> findAll() {
    return ordersRepository.findAll();
  }

  public GetOrderDetailsResultSet orderDetails(Integer orderNumber) {
    return ordersRepository.orderDetails(orderNumber);
  }

  public OrdersEntity findByOrderNumber(Integer orderNumber) {
    return ordersRepository.findByOrderNumber(orderNumber);
  }
  public boolean existsByOrderNumberAndBookNumber(Integer orderNumber, Integer bookNumber) {
    return orderItemsRepository.existsByOrderNumberAndBookNumber(orderNumber,bookNumber);
  }

}
