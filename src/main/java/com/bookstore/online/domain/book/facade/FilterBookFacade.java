package com.bookstore.online.domain.book.facade;

import com.bookstore.online.domain.book.dto.response.GetBookListResponseDto;
import com.bookstore.online.domain.book.entity.BooksEntity;
import com.bookstore.online.domain.book.entity.resultSet.GetBookOrderCountResultSet;
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
public class FilterBookFacade {

  private final ReadBookService readBookService;
  private final ReadCategoryService readCategoryService;

  public ResponseEntity<? super GetBookListResponseDto> getBookDiscountList() {
    List<Book> bookList = new ArrayList<>();
    List<BooksEntity> booksEntityList = new ArrayList<>();
    try {
      booksEntityList = readBookService.findBookDiscountList();
      for (BooksEntity booksEntity : booksEntityList) {
        Integer categoryNumber = booksEntity.getCategoryNumber();
        CategoryEntity categoryEntity = readCategoryService.findCategoryNumber(categoryNumber);
        Book book = new Book(booksEntity, categoryEntity);
        bookList.add(book);
      }

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return GetBookListResponseDto.success(bookList);
  }

  // 추후 추가 기능
//  public ResponseEntity<? super GetBookListResponseDto> getBestSellerBookList() {
//    List<GetBookOrderCountResultSet> resultSetList = readBookService.bookOrderCount();
//    System.out.println(resultSetList);
//    List<Book> bookList = new ArrayList<>();
//    try {
//      for (GetBookOrderCountResultSet resultSet : resultSetList) {
//
//        Integer bookNumber = resultSet.bookNumber();
//        BooksEntity booksEntity = readBookService.findBookNumber(bookNumber);
//        Integer categoryNumber = booksEntity.getCategoryNumber();
//        CategoryEntity categoryEntity = readCategoryService.findCategoryNumber(categoryNumber);
//        Book book = new Book(booksEntity, categoryEntity);
//        bookList.add(book);
//      }
//    } catch (Exception exception) {
//      exception.printStackTrace();
//      return ResponseDto.databaseError();
//    }
//    return GetBookListResponseDto.success(bookList);
//  }

  public ResponseEntity<? super GetBookListResponseDto> getRecentlyBookList() {
    List<Book> bookList = new ArrayList<>();
    List<BooksEntity> booksEntityList = new ArrayList<>();
    try {
      booksEntityList = readBookService.resentlyBookList();
      for (BooksEntity booksEntity : booksEntityList) {
        Integer categoryNumber = booksEntity.getCategoryNumber();
        CategoryEntity categoryEntity = readCategoryService.findCategoryNumber(categoryNumber);
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
