package com.bookstore.online.domain.book.facade;

import com.bookstore.online.domain.book.dto.request.PostCreateBookRequestDto;
import com.bookstore.online.domain.book.entity.BooksEntity;
import com.bookstore.online.domain.book.service.CreateBookService;
import com.bookstore.online.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateBookFacade {
  private final CreateBookService createBookService;

  public ResponseEntity<ResponseDto> postCreateBook(PostCreateBookRequestDto dto){
    try{
      BooksEntity booksEntity = new BooksEntity(dto);
      createBookService.createBook(booksEntity);
    }catch (Exception exception){
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success();
  }
}
