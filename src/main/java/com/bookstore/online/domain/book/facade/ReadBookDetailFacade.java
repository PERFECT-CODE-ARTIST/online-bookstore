package com.bookstore.online.domain.book.facade;

import com.bookstore.online.domain.book.dto.response.GetBookDetailResponseDto;
import com.bookstore.online.domain.book.entity.BooksEntity;
import com.bookstore.online.domain.book.service.ReadBookService;
import com.bookstore.online.domain.category.entity.CategoryEntity;
import com.bookstore.online.domain.category.service.ReadCategoryService;
import com.bookstore.online.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadBookDetailFacade {

  private final ReadBookService readBookService;
  private final ReadCategoryService readCategoryService;

  public ResponseEntity<? super GetBookDetailResponseDto> bookDetail(Integer bookNumber) {
    BooksEntity booksEntity = null;
    CategoryEntity categoryEntity = null;
    try {
      booksEntity = readBookService.findBookNumber(bookNumber);
      if (booksEntity == null) {
        throw new Error("존재하지 않는 책입니다.");
      }

      Integer categoryNumber = booksEntity.getCategoryNumber();

      categoryEntity = readCategoryService.findCategoryNumber(categoryNumber);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return GetBookDetailResponseDto.success(booksEntity, categoryEntity);
//    return ResponseDto.success();
  }
}
