package com.bookstore.online.domain.book.facade;

import com.bookstore.online.domain.book.dto.request.PatchUpdateBookRequestDto;
import com.bookstore.online.domain.book.dto.request.PostCreateBookRequestDto;
import com.bookstore.online.domain.book.dto.response.GetBookDetailResponseDto;
import com.bookstore.online.domain.book.dto.response.GetBookListResponseDto;
import com.bookstore.online.domain.book.entity.BooksEntity;
import com.bookstore.online.domain.book.entity.resultSet.GetBookOrderCountResultSet;
import com.bookstore.online.domain.book.entity.resultSet.GetUserOrderPurchasedBookResultSet;
import com.bookstore.online.domain.book.object.Book;
import com.bookstore.online.domain.book.service.CreateBookService;
import com.bookstore.online.domain.book.service.DeleteBookService;
import com.bookstore.online.domain.book.service.ReadBookService;
import com.bookstore.online.domain.book.service.UpdateBookService;
import com.bookstore.online.domain.category.entity.CategoryEntity;
import com.bookstore.online.domain.category.service.ReadCategoryService;
import com.bookstore.online.domain.user.entity.UserEntity;
import com.bookstore.online.domain.user.service.ReadUserService;
import com.bookstore.online.global.dto.ResponseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class BookFacade {

  private final CreateBookService createBookService;
  private final UpdateBookService updateBookService;
  private final ReadBookService readBookService;
  private final DeleteBookService deleteBookService;
  private final ReadCategoryService readCategoryService;
  private final ReadUserService readUserService;

  public ResponseEntity<ResponseDto> postCreateBook(PostCreateBookRequestDto dto) {
    try {
      BooksEntity booksEntity = new BooksEntity(dto);
      createBookService.createBook(booksEntity);
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success();
  }

  @Transactional
  public ResponseEntity<ResponseDto> patchBook(PatchUpdateBookRequestDto dto, Integer bookNumber) {
    try {
      BooksEntity booksEntity = readBookService.findBookNumber(bookNumber);

      if (booksEntity == null) {
        throw new Error("존재하지 않는 책입니다.");
      }
      booksEntity.patch(dto);
      updateBookService.updateBook(booksEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success();
  }

  @Transactional
  public ResponseEntity<ResponseDto> deletebook(Integer bookNumber) {
    try {
      BooksEntity booksEntity = readBookService.findBookNumber(bookNumber);
      if (booksEntity == null) {
        throw new Error("존재하지 않는 책입니다.");
      }
      deleteBookService.deleteBook(booksEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success();
  }

  public ResponseEntity<? super GetBookListResponseDto> getAllBookList(Pageable pageable) {
    List<Book> bookList = new ArrayList<>();
    List<BooksEntity> booksEntityList = new ArrayList<>();
    try {
      booksEntityList = readBookService.BookList(pageable);
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
  }

  public ResponseEntity<? super GetBookListResponseDto> getSearchBookList(Integer categoryNumber,
      String orderSet, Integer page) {
    List<Book> bookList = new ArrayList<>();
    List<BooksEntity> booksEntityList = new ArrayList<>();
    try {
      Integer paging = 10 * (page - 1);

      booksEntityList = readBookService.searchBookList(categoryNumber, orderSet, paging);
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

  public ResponseEntity<? super GetBookListResponseDto> getBookDiscountList(Integer page) {
    List<Book> bookList = new ArrayList<>();
    List<BooksEntity> booksEntityList = new ArrayList<>();
    Integer paging = 10 * (page - 1);
    try {
      booksEntityList = readBookService.findBookDiscountList(paging);
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

  public ResponseEntity<? super GetBookListResponseDto> getBestSellerBookList() {
    List<GetBookOrderCountResultSet> resultSetList = readBookService.bookOrderCount();
    List<Book> bookList = new ArrayList<>();
    try {
      for (GetBookOrderCountResultSet resultSet : resultSetList) {

        Integer bookNumber = resultSet.bookNumber();
        BooksEntity booksEntity = readBookService.findBookNumber(bookNumber);
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
  public ResponseEntity<? super GetBookListResponseDto> getCategoryBestSellerBookList(
      String userId) {
    List<Book> bookList = new ArrayList<>();
    List<BooksEntity> booksEntityList =new ArrayList<>();
    try {
 UserEntity userEntity = readUserService .findUserByUserId(userId);
      if (userEntity == null) {
        throw new Error("존재하지 않는 유저 입니다.");
      }
      GetUserOrderPurchasedBookResultSet resultSet = readBookService.userPurchasedBookList(userId);
      if (resultSet == null) {
        throw new Error("구매한 목록이 없습니다.");
      }
      Integer categoryNumber = resultSet.categoryNumber();
      List<GetBookOrderCountResultSet> bestSellerBookNumber = readBookService.bookOrderCount();
      if (bestSellerBookNumber.isEmpty()) {
        throw new Error("베스트셀러 목록이 없습니다.");
      }
      for (GetBookOrderCountResultSet bestSellerBook : bestSellerBookNumber) {
        Integer bookNumber = bestSellerBook.bookNumber();
        BooksEntity booksEntity = readBookService.findBookNumber(bookNumber);
        if (booksEntity.getCategoryNumber().equals(categoryNumber)) {
          CategoryEntity categoryEntity = readCategoryService.findCategoryNumber(categoryNumber);
          Book book = new Book(booksEntity, categoryEntity);
          bookList.add(book);
        }
      }

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return GetBookListResponseDto.success(bookList);
  }
}
