package com.bookstore.online.domain.category.facade;

import com.bookstore.online.domain.category.entity.CategoryEntity;
import com.bookstore.online.domain.category.service.DeleteCategoryService;
import com.bookstore.online.domain.category.service.ReadCategoryService;
import com.bookstore.online.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DeleteCategoryFacade {
  private final DeleteCategoryService deleteCategoryService;


  public ResponseEntity<ResponseDto> deleteCategory(Integer categoryNumber){
    try{
      CategoryEntity categoryEntity = deleteCategoryService.findCategoryNumber(categoryNumber);
      if(categoryEntity == null) throw new Error("존재 하지 않는 카테고리 입니다.");
      deleteCategoryService.deleteCategory(categoryNumber);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success();
  }
}
