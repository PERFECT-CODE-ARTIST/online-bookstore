package com.bookstore.online.domain.orders.entity.result;

public record GetOrderDetailsResultSet (Integer orderNumber, String userId, String name, String orderDate, Integer totalPrice, String status) {

}


