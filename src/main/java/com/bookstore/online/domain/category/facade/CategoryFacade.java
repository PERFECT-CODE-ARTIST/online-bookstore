package com.bookstore.online.domain.category.facade;

import com.bookstore.online.domain.category.dto.request.PatchEditCategoryRequestDto;
import com.bookstore.online.domain.category.dto.request.PostCreateCategoryRequestDto;
import com.bookstore.online.domain.category.dto.response.GetCategoryListResponseDto;
import com.bookstore.online.domain.category.entity.CategoryEntity;
import com.bookstore.online.domain.category.service.CreateCategoryService;
import com.bookstore.online.domain.category.service.DeleteCategoryService;
import com.bookstore.online.domain.category.service.PatchCategoryService;
import com.bookstore.online.domain.category.service.ReadCategoryService;
import com.bookstore.online.global.dto.ResponseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CategoryFacade {

  private final CreateCategoryService createCategoryService;
  private final DeleteCategoryService deleteCategoryService;
  private final PatchCategoryService patchCategoryService;
  private final ReadCategoryService readCategoryService;

  @Transactional
  public ResponseEntity<ResponseDto> postCreateCategory(PostCreateCategoryRequestDto dto) {
    try {
      CategoryEntity categoryEntity = new CategoryEntity(dto);
      createCategoryService.createCategory(categoryEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    return ResponseDto.success();
  }

  @Transactional
  public ResponseEntity<ResponseDto> deleteCategory(Integer categoryNumber) {
    try {
      CategoryEntity categoryEntity = deleteCategoryService.findCategoryNumber(categoryNumber);
      if (categoryEntity == null) {
        throw new Error("존재 하지 않는 카테고리 입니다.");
      }
      deleteCategoryService.deleteCategory(categoryNumber);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success();
  }

  @Transactional
  public ResponseEntity<ResponseDto> patchCategory(PatchEditCategoryRequestDto dto,
      Integer categoryNumber) {
    try {
      CategoryEntity categoryEntity = patchCategoryService.findCategoryNumber(categoryNumber);
      if (categoryEntity == null) {
        throw new Error("존재 하지 않는 카테고리 입니다.");
      }
      categoryEntity.patchCategory(dto);
      patchCategoryService.updateCategory(categoryEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success();
  }

  @Transactional(readOnly = true)
  public ResponseEntity<? super GetCategoryListResponseDto> getCategoryList(Pageable pageable) {
    List<CategoryEntity> categoryEntityList = new ArrayList<>();
    try {
      categoryEntityList = readCategoryService.readCategoryAllList(pageable);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return GetCategoryListResponseDto.success(categoryEntityList);
  }


}
