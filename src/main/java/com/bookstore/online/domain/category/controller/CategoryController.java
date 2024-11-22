package com.bookstore.online.domain.category.controller;

import com.bookstore.online.domain.category.dto.request.PatchEditCategoryRequestDto;
import com.bookstore.online.domain.category.dto.request.PostCreateCategoryRequestDto;
import com.bookstore.online.domain.category.dto.response.GetCategoryListResponseDto;
import com.bookstore.online.domain.category.facade.CategoryFacade;
import com.bookstore.online.global.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryFacade categoryFacade;

  // localhost:8080/api/v1/category?page=0&size=5 0번페이지에 5개를 불러온다.
  @GetMapping(value = {"", "/"})
  public ResponseEntity<? super GetCategoryListResponseDto> getCategory(
      Pageable pageable
  ) {
    ResponseEntity<? super GetCategoryListResponseDto> response = categoryFacade.getCategoryList(
        pageable);
    return response;
  }

  @PostMapping("/")
  public ResponseEntity<ResponseDto> postCategory(
      @RequestBody @Valid PostCreateCategoryRequestDto requestBody
  ) {
    ResponseEntity<ResponseDto> response = categoryFacade.postCreateCategory(requestBody);
    return response;
  }

  @PatchMapping("/{categoryNumber}")
  public ResponseEntity<ResponseDto> updateCategory(
      @RequestBody @Valid PatchEditCategoryRequestDto requestBody,
      @PathVariable("categoryNumber") Integer categoryNumber
  ) {
    ResponseEntity<ResponseDto> response = categoryFacade.patchCategory(requestBody,
        categoryNumber);
    return response;
  }

  @DeleteMapping("/{categoryNumber}")
  public ResponseEntity<ResponseDto> deleteCategory(
      @PathVariable("categoryNumber") Integer categoryNumber
  ) {
    ResponseEntity<ResponseDto> response = categoryFacade.deleteCategory(categoryNumber);
    return response;
  }

}
