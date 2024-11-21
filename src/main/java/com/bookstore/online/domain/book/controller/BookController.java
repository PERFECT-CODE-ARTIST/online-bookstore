package com.bookstore.online.domain.book.controller;

import com.bookstore.online.domain.book.dto.request.PatchUpdateBookRequestDto;
import com.bookstore.online.domain.book.dto.request.PostCreateBookRequestDto;
import com.bookstore.online.domain.book.dto.response.GetBookDetailResponseDto;
import com.bookstore.online.domain.book.dto.response.GetBookListResponseDto;
import com.bookstore.online.domain.book.facade.AlgorithmBookFacde;
import com.bookstore.online.domain.book.facade.CreateBookFacade;
import com.bookstore.online.domain.book.facade.DeleteBookFacade;
import com.bookstore.online.domain.book.facade.FilterBookFacade;
import com.bookstore.online.domain.book.facade.PatchBookFacade;
import com.bookstore.online.domain.book.facade.ReadBookDetailFacade;
import com.bookstore.online.domain.book.facade.ReadBookFacade;
import com.bookstore.online.domain.book.facade.SearchBookFacade;
import com.bookstore.online.global.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

  private final CreateBookFacade createBookFacade;
  private final ReadBookFacade readBookFacade;
  private final FilterBookFacade filterBookFacade;
  private final AlgorithmBookFacde algorithmBookFacade;
  private final SearchBookFacade searchBookFacade;
  private final PatchBookFacade patchBookFacade;
  private final DeleteBookFacade deleteBookFacade;
  private final ReadBookDetailFacade readBookDetailFacade;


  @GetMapping(value = {"", "/"})
  public ResponseEntity<? super GetBookListResponseDto> getAllBookList() {
    ResponseEntity<? super GetBookListResponseDto> response = readBookFacade.getAllBookList();
    return response;
  }

  @GetMapping("/{bookNumber}")
  public ResponseEntity<? super GetBookDetailResponseDto> getBookDetail(
      @PathVariable("bookNumber") Integer bookNumber
  ) {
    ResponseEntity<? super GetBookDetailResponseDto> response = readBookDetailFacade.bookDetail(bookNumber);
    return response;
  }


  @PostMapping(value = {"", "/"})
  public ResponseEntity<ResponseDto> postBook(
      @RequestBody @Valid PostCreateBookRequestDto requestBody
  ) {
    ResponseEntity<ResponseDto> response = createBookFacade.postCreateBook(requestBody);
    return response;
  }

  @PatchMapping("/{bookNumber}")
  public ResponseEntity<ResponseDto> patchBook(
      @RequestBody @Valid PatchUpdateBookRequestDto requestBody,
      @PathVariable("bookNumber") Integer bookNumber
  ){
    ResponseEntity<ResponseDto> response = patchBookFacade.patchBook(requestBody,bookNumber);
    return response;
  }

  @DeleteMapping("/{bookNumber}")
  public ResponseEntity<ResponseDto> deleteBook(
      @PathVariable("bookNumber") Integer bookNumber
  ){
    ResponseEntity<ResponseDto> response = deleteBookFacade.deletebook(bookNumber);
    return response;
  }


  @GetMapping("/search")
  public ResponseEntity<? super GetBookListResponseDto> getBooksList(
      @RequestParam("categoryNumber") Integer categoryNumber,
      @RequestParam("orderSet") String orderSet
  ) {
    ResponseEntity<? super GetBookListResponseDto> response = searchBookFacade.getSearchBookList(
        categoryNumber, orderSet);
    return response;
  }

  @GetMapping("/discounts")
  public ResponseEntity<? super GetBookListResponseDto> getBooksDiscountsList() {
    ResponseEntity<? super GetBookListResponseDto> response = filterBookFacade.getBookDiscountList();
    return response;
  }

  @GetMapping("/recently-books")
  public ResponseEntity<? super GetBookListResponseDto> getBooksRecentlyBooksList() {
    ResponseEntity<? super GetBookListResponseDto> response = filterBookFacade.getRecentlyBookList();
    return response;
  }

 // 추후 추가 기능
//  @GetMapping("/best-seller")
//  public ResponseEntity<? super GetBookListResponseDto> getBooksBestSellerList() {
//    ResponseEntity<? super GetBookListResponseDto> response = filterBookFacade.getBestSellerBookList();
//    return response;
//  }


//  @GetMapping("/recommend/category-best-seller")
//  public ResponseEntity<? super GetBookListResponseDto> getBooksCategoryBestSellerList(
//      @AuthenticationPrincipal String userId
//  ) {
//    System.out.println(userId);
//    ResponseEntity<? super GetBookListResponseDto> response = algorithmBookFacade.getCategoryBestSellerBookList(userId);
//    return response;
//  }
}
