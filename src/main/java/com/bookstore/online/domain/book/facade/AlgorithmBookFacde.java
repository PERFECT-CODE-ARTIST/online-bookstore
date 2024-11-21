package com.bookstore.online.domain.book.facade;

import com.bookstore.online.domain.book.dto.response.GetBookListResponseDto;
import com.bookstore.online.domain.book.entity.BooksEntity;
import com.bookstore.online.domain.book.entity.resultSet.GetUserOrderPurchasedBookResultSet;
import com.bookstore.online.domain.book.object.Book;
import com.bookstore.online.domain.book.service.ReadBookService;
import com.bookstore.online.domain.category.entity.CategoryEntity;
import com.bookstore.online.domain.category.service.ReadCategoryService;
import com.bookstore.online.domain.user.entity.UserEntity;
import com.bookstore.online.domain.user.service.ReadUserService;
import com.bookstore.online.global.dto.ResponseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlgorithmBookFacde {

  private final ReadBookService readBookService;
  private final ReadCategoryService readCategoryService;
  private final ReadUserService readUserService;
// 추후 추가 기능
  public ResponseEntity<? super GetBookListResponseDto> getCategoryBestSellerBookList(String userId) {
//    List<Book> bookList = new ArrayList<>();
//    List<BooksEntity> booksEntityList =new ArrayList<>();
//    try {
//     UserEntity userEntity  = readUserService.findUserByUserId(userId);
//     if(userEntity == null) throw new Error("존재 하지 않는 유저 입니다.");
//
//      GetUserOrderPurchasedBookResultSet resultSet = readBookService.userPurchasedBookList(userId); //현재 고정값 넣어둠
//      if(resultSet == null ) {
//        throw new Error("구매한 목록이 없습니다123.");
//      }
//
//      Integer categoryNumber = resultSet.categoryNumber().intValue();
//      booksEntityList = readBookService.findCategoryNumber(categoryNumber);
//      for (BooksEntity booksEntity : booksEntityList) {
//        Integer bookNumber = booksEntity.getBookNumber();
//        CategoryEntity categoryEntity = readCategoryService.findCategoryNumber(categoryNumber);
//        Book book = new Book(booksEntity, categoryEntity);
//        bookList.add(book);
//      }
//
//    } catch (Exception exception) {
//      exception.printStackTrace();
//      return ResponseDto.databaseError();
//    }
//    return GetBookListResponseDto.success(bookList);
    return null;
  }
}
