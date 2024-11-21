package com.bookstore.online.domain.book.facade;

import com.bookstore.online.domain.book.entity.BooksEntity;
import com.bookstore.online.domain.book.service.DeleteBookService;
import com.bookstore.online.domain.book.service.ReadBookService;
import com.bookstore.online.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class DeleteBookFacade {

  private final ReadBookService readBookService;
  private final DeleteBookService deleteBookService;

  public ResponseEntity<ResponseDto> deletebook(Integer bookNumber) {
    try {
      BooksEntity booksEntity = readBookService.findBookNumber(bookNumber);
      if(booksEntity == null) throw new Error("존재하지 않는 책입니다.");
      deleteBookService.deleteBook(booksEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success();
  }
}
