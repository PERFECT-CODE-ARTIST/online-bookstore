package com.bookstore.online.domain.book.facade;

import com.bookstore.online.domain.book.dto.request.PatchUpdateBookRequestDto;
import com.bookstore.online.domain.book.entity.BooksEntity;
import com.bookstore.online.domain.book.service.ReadBookService;
import com.bookstore.online.domain.book.service.UpdateBookService;
import com.bookstore.online.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatchBookFacade {

  private final UpdateBookService updateBookService;
  private final ReadBookService readBookService;

  public ResponseEntity<ResponseDto> patchBook(PatchUpdateBookRequestDto dto, Integer bookNumber) {
    try {
      BooksEntity booksEntity = readBookService.findBookNumber(bookNumber);

      if(booksEntity == null) throw new Error("존재하지 않는 책입니다.");
      booksEntity.patch(dto);
      updateBookService.updateBook(booksEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success();
  }
}
