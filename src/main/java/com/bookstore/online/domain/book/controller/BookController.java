package com.bookstore.online.domain.book.controller;

import com.bookstore.online.domain.book.dto.request.PatchUpdateBookRequestDto;
import com.bookstore.online.domain.book.dto.request.PostCreateBookRequestDto;
import com.bookstore.online.domain.book.dto.response.GetBookDetailResponseDto;
import com.bookstore.online.domain.book.dto.response.GetBookListResponseDto;
import com.bookstore.online.domain.book.facade.BookFacade;
import com.bookstore.online.global.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {

 private final BookFacade bookFacade;

  //localhost:8080/api/v1/book?page=0&size=10
  @GetMapping(value = {"", "/"})
  public ResponseEntity<? super GetBookListResponseDto> getAllBookList(
      Pageable pageable
  ) {
    ResponseEntity<? super GetBookListResponseDto> response = bookFacade.getAllBookList(pageable);
    return response;
  }

  @GetMapping("/{bookNumber}")
  public ResponseEntity<? super GetBookDetailResponseDto> getBookDetail(
      @PathVariable("bookNumber") Integer bookNumber
  ) {
    ResponseEntity<? super GetBookDetailResponseDto> response = bookFacade.bookDetail(bookNumber);
    return response;
  }


  @PostMapping(value = {"", "/"})
  public ResponseEntity<ResponseDto> postBook(
      @RequestBody @Valid PostCreateBookRequestDto requestBody
  ) {
    ResponseEntity<ResponseDto> response = bookFacade.postCreateBook(requestBody);
    return response;
  }

  @PatchMapping("/{bookNumber}")
  public ResponseEntity<ResponseDto> patchBook(
      @RequestBody @Valid PatchUpdateBookRequestDto requestBody,
      @PathVariable("bookNumber") Integer bookNumber
  ){
    ResponseEntity<ResponseDto> response = bookFacade.patchBook(requestBody,bookNumber);
    return response;
  }

  @DeleteMapping("/{bookNumber}")
  public ResponseEntity<ResponseDto> deleteBook(
      @PathVariable("bookNumber") Integer bookNumber
  ){
    ResponseEntity<ResponseDto> response = bookFacade.deletebook(bookNumber);
    return response;
  }


  @GetMapping("/search")
  public ResponseEntity<? super GetBookListResponseDto> getBooksList(
      @RequestParam("categoryNumber") Integer categoryNumber,
      @RequestParam("orderSet") String orderSet,
      @RequestParam("page") Integer page
  ) {
    ResponseEntity<? super GetBookListResponseDto> response = bookFacade.getSearchBookList(categoryNumber, orderSet, page);
    return response;
  }

  @GetMapping("/discounts")
  public ResponseEntity<? super GetBookListResponseDto> getBooksDiscountsList(
      @RequestParam("page") Integer page
  ) {
    ResponseEntity<? super GetBookListResponseDto> response = bookFacade.getBookDiscountList(page);
    return response;
  }

  @GetMapping("/recently-books")
  public ResponseEntity<? super GetBookListResponseDto> getBooksRecentlyBooksList() {
    ResponseEntity<? super GetBookListResponseDto> response = bookFacade.getRecentlyBookList();
    return response;
  }

 // 추후 추가 기능
//  @GetMapping("/best-seller")
//  public ResponseEntity<? super GetBookListResponseDto> getBooksBestSellerList() {
//    ResponseEntity<? super GetBookListResponseDto> response = bookFacade.getBestSellerBookList();
//    return response;
//  }


//  @GetMapping("/recommend/category-best-seller")
//      @AuthenticationPrincipal String userId
//  ) {
//    ResponseEntity<? super GetBookListResponseDto> response = bookFacade.getCategoryBestSellerBookList(userId);
//    return response;
//  }
}
