package com.bookstore.online.domain.book.facade;

import com.bookstore.online.domain.book.dto.response.GetBookListResponseDto;
import com.bookstore.online.domain.book.entity.BooksEntity;
import com.bookstore.online.domain.book.object.Book;
import com.bookstore.online.domain.book.service.ReadBookService;
import com.bookstore.online.domain.category.entity.CategoryEntity;
import com.bookstore.online.domain.category.service.ReadCategoryService;
import com.bookstore.online.global.dto.ResponseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchBookFacade {
  private final ReadCategoryService readCategoryService;
  private final ReadBookService readBookService;

  public ResponseEntity<? super GetBookListResponseDto> getSearchBookList(Integer categoryNumber, String orderSet) {
    List<Book> bookList = new ArrayList<>();
    List<BooksEntity> booksEntityList = new ArrayList<>();
    try {
      booksEntityList = readBookService.searchBookList(categoryNumber, orderSet);
      for (BooksEntity booksEntity : booksEntityList) {
        Integer categoryNumebr = booksEntity.getCategoryNumber();
        CategoryEntity categoryEntity = readCategoryService.findCategoryNumber(categoryNumebr);
        Book book = new Book(booksEntity, categoryEntity);
        bookList.add(book);
      }
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return GetBookListResponseDto.success(bookList);
  }
}
