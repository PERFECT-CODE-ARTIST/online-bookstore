package com.bookstore.online.domain.orders.entity.repository;

import com.bookstore.online.domain.orders.entity.result.GetOrderDetailsResultSet;
import com.bookstore.online.domain.orders.entity.OrdersEntity;
import com.bookstore.online.global.dto.ResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Integer> {

  OrdersEntity findByOrderNumber(Integer orderNumber);

  @Query(value =
      "SELECT new com.bookstore.online.domain.orders.entity.result.GetOrderDetailsResultSet( " +
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
  GetOrderDetailsResultSet orderDetails(@Param("orderNumber") Integer orderNumber);

}
