package com.bookstore.online.domain.orders.entity.repository;

import com.bookstore.online.domain.orders.entity.OrderItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItemsEntity, Integer> {

  boolean existsByOrderNumberAndBookNumber(Integer orderNumber, Integer bookNumber);
}
