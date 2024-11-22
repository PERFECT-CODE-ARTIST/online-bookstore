package com.bookstore.online.domain.book.dto.response;

import com.bookstore.online.domain.book.entity.BooksEntity;
import com.bookstore.online.domain.category.entity.CategoryEntity;
import com.bookstore.online.global.dto.ResponseCode;
import com.bookstore.online.global.dto.ResponseDto;
import com.bookstore.online.global.dto.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@AllArgsConstructor
public class GetBookDetailResponseDto extends ResponseDto {

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

  public GetBookDetailResponseDto(BooksEntity booksEntity, CategoryEntity categoryEntity) {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
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

  public static ResponseEntity<GetBookDetailResponseDto> success(BooksEntity booksEntity,
      CategoryEntity categoryEntity) {
    GetBookDetailResponseDto responseBody = new GetBookDetailResponseDto(booksEntity,
        categoryEntity);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }


}
