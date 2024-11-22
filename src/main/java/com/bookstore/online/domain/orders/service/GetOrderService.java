package com.bookstore.online.domain.orders.service;

import com.bookstore.online.domain.orders.dto.request.GetOrderDetailsDTO;
import com.bookstore.online.domain.orders.entity.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetOrderService {

  private final OrdersRepository ordersRepository;

  public GetOrderDetailsDTO orderDetails(Integer orderNumber) {
    return ordersRepository.findByOrderNumber(orderNumber);
  }
}
