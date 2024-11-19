package com.bookstore.online.domain.category.facade;

import com.bookstore.online.domain.category.dto.response.GetCategoryListResponseDto;
import com.bookstore.online.domain.category.entity.CategoryEntity;
import com.bookstore.online.domain.category.entity.repository.CategoryRepository;
import com.bookstore.online.domain.category.service.ReadCategoryService;
import com.bookstore.online.global.dto.ResponseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReadCategoryFacade {

  private final CategoryRepository categoryRepository;
  private final ReadCategoryService readCategoryService;

  public ResponseEntity<? super GetCategoryListResponseDto> getCategoryList() {
    List<CategoryEntity> categoryEntityList = new ArrayList<>();
    try {
      categoryEntityList = readCategoryService.readCategoryAllList();

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return GetCategoryListResponseDto.success(categoryEntityList);
  }
}
