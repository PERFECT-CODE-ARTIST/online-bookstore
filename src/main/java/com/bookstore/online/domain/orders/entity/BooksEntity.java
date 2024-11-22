package com.bookstore.online.domain.orders.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Books")
@Table(name = "books")
public class BooksEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer bookNumber; // 책 고유번호
  String bookName; // 책 이름
  String author; // 작가명
  String publishingDate; // 출간일
  String registrationDate; // 등록일
  Integer discountRate; // 할인률
  Integer categoryNumber; // 카테고리 고유번호
  String publisher; // 출판사/ 출판인
  Integer bookPrice; // 책 가격
  Integer bookCount; // 책 총 수량

}
