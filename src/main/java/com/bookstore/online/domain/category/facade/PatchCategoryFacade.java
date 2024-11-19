package com.bookstore.online.domain.category.facade;

import com.bookstore.online.domain.category.dto.request.PatchEditCategoryRequestDto;
import com.bookstore.online.domain.category.entity.CategoryEntity;
import com.bookstore.online.domain.category.service.PatchCategoryService;
import com.bookstore.online.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PatchCategoryFacade {

  private final PatchCategoryService patchCategoryService;

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
}
