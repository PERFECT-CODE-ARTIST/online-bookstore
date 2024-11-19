package com.bookstore.online.domain.category.controller;

import com.bookstore.online.domain.category.dto.request.PatchEditCategoryRequestDto;
import com.bookstore.online.domain.category.dto.request.PostCreateCategoryRequestDto;
import com.bookstore.online.domain.category.dto.response.GetCategoryListResponseDto;
import com.bookstore.online.domain.category.facade.CreateCategoryFacade;
import com.bookstore.online.domain.category.facade.DeleteCategoryFacade;
import com.bookstore.online.domain.category.facade.PatchCategoryFacade;
import com.bookstore.online.domain.category.facade.ReadCategoryFacade;
import com.bookstore.online.global.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

  private final CreateCategoryFacade createCategoryFacade;
  private final ReadCategoryFacade readCategoryFacade;
  private final DeleteCategoryFacade deleteCategoryFacade;
  private final PatchCategoryFacade patchCategoryFacade;

  @GetMapping(value = {"","/"})
  public ResponseEntity<? super GetCategoryListResponseDto> getCategory() {
    ResponseEntity<? super GetCategoryListResponseDto> response = readCategoryFacade.getCategoryList();
    return response;
  }

  @PostMapping("/")
  public ResponseEntity<ResponseDto> postCategory(
      @RequestBody @Valid PostCreateCategoryRequestDto requestBody
  ){
    ResponseEntity<ResponseDto> response  = createCategoryFacade.postCreateCategory(requestBody);
    return response;
  }

  @PatchMapping("/{categoryNumber}")
  public ResponseEntity<ResponseDto> updateCategory(
      @RequestBody @Valid PatchEditCategoryRequestDto requestBody,
      @PathVariable("categoryNumber") Integer categoryNumber
  ){
    ResponseEntity<ResponseDto> response = patchCategoryFacade.patchCategory(requestBody, categoryNumber);
    return response;
  }

  @DeleteMapping("/{categoryNumber}")
  public ResponseEntity<ResponseDto> deleteCategory(
      @PathVariable("categoryNumber") Integer categoryNumber
  ){
    ResponseEntity<ResponseDto> response = deleteCategoryFacade.deleteCategory(categoryNumber);
    return response;
  }

}
