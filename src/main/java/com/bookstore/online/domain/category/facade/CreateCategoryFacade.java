package com.bookstore.online.domain.category.facade;


import com.bookstore.online.domain.category.dto.request.PostCreateCategoryRequestDto;
import com.bookstore.online.domain.category.entity.CategoryEntity;
import com.bookstore.online.domain.category.service.CreateCategoryService;
import com.bookstore.online.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateCategoryFacade {

  private final CreateCategoryService createCategoryService;

  public ResponseEntity<ResponseDto> postCreateCategory(PostCreateCategoryRequestDto dto){
    try{
      CategoryEntity categoryEntity = new CategoryEntity(dto);
      createCategoryService.createCategory(categoryEntity);

    }catch (Exception exception){
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success();
  }

}
