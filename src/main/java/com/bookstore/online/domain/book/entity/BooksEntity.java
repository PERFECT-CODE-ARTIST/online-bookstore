package com.bookstore.online.domain.book.entity;

import com.bookstore.online.domain.book.dto.request.PatchUpdateBookRequestDto;
import com.bookstore.online.domain.book.dto.request.PostCreateBookRequestDto;
import com.bookstore.online.global.customDate.CustomDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "books")
@Table(name = "books")
public class BooksEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer bookNumber;
  private String bookName;
  private String author;
  private Integer bookPrice;
  private Integer discountRate;
  private String publisher;
  private String publishingDate;
  private String registrationDate;
  private Integer categoryNumber;
  @Setter
  private Integer bookCount;

  public BooksEntity(PostCreateBookRequestDto dto) {
    this.bookName = dto.getBookName();
    this.author = dto.getAuthor();
    this.bookPrice = dto.getBookPrice();
    this.discountRate = dto.getDiscountRate();
    this.publisher = dto.getPublisher();
    this.publishingDate = dto.getPublishingDate();
    this.registrationDate = CustomDate.LocalDateTimeFormat();
    this.categoryNumber = dto.getCategoryNumber();
    this.bookCount = dto.getBookCount();
  }

  public void patch(PatchUpdateBookRequestDto dto) {
    this.bookName = dto.getBookName();
    this.author = dto.getAuthor();
    this.bookPrice = dto.getBookPrice();
    this.discountRate = dto.getDiscountRate();
    this.publisher = dto.getPublisher();
    this.publishingDate = dto.getPublishingDate();
    this.registrationDate = CustomDate.LocalDateTimeFormat();
    this.categoryNumber = dto.getCategoryNumber();
    this.bookCount = dto.getBookCount();
  }
}
