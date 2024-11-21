package com.bookstore.online.domain.book.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchUpdateBookRequestDto {
  @NotNull
  private String bookName;
  @NotNull
  private String author;
  @NotNull
  private Integer bookPrice;
  private Integer discountRate;
  @NotNull
  private String publisher;
  @NotNull
  private String publishingDate;
  @NotNull
  private Integer categoryNumber;
  @NotNull
  private Integer bookCount;
}
