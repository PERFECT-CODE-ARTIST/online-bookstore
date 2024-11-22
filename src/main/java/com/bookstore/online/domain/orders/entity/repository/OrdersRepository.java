package com.bookstore.online.domain.orders.entity.repository;

import com.bookstore.online.domain.orders.dto.result.GetOrderDetailsDTO;
import com.bookstore.online.domain.orders.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Integer> {

  @Query(value =
      "SELECT new com.bookstore.online.domain.orders.dto.result.GetOrderDetailsDTO( " +
          "    o.orderNumber, " +
          "    o.userId, " +
          "    u.name, " +
          "    o.orderDate, " +
          "    o.totalPrice, " +
          "    o.status) " +
          "FROM Orders o JOIN users u " +
          "ON o.userId = u.userId " +
          "WHERE o.orderNumber = :orderNumber "
  )
  GetOrderDetailsDTO orderDetails(@Param("orderNumber") Integer orderNumber);

  OrdersEntity findByOrderNumber(Integer orderNumber);

}
