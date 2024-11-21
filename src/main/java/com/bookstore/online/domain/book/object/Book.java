package com.bookstore.online.domain.book.object;

import com.bookstore.online.domain.book.entity.BooksEntity;
import com.bookstore.online.domain.category.entity.CategoryEntity;
import lombok.Getter;

@Getter
public class Book {
  private Integer bookNumber;
  private String bookName;
  private String author;
  private Integer bookPrice;
  private Integer discountRate;
  private String publisher;
  private String publishingDate;
  private String registrationDate;
  private String categoryName;
  private Integer bookCount;

  public Book(BooksEntity booksEntity, CategoryEntity categoryEntity) {
    this.bookNumber = booksEntity.getBookNumber();
    this.bookName = booksEntity.getBookName();
    this.author = booksEntity.getAuthor();
    this.bookPrice = booksEntity.getBookPrice();
    this.discountRate = booksEntity.getDiscountRate();
    this.publisher = booksEntity.getPublisher();
    this.publishingDate = booksEntity.getPublishingDate();
    this.registrationDate = booksEntity.getRegistrationDate();
    this.categoryName = categoryEntity.getCategoryName();
    this.bookCount = booksEntity.getBookCount();
  }
}
