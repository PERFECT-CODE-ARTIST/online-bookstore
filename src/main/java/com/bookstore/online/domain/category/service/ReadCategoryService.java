package com.bookstore.online.domain.category.service;

import com.bookstore.online.domain.category.entity.CategoryEntity;
import com.bookstore.online.domain.category.entity.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadCategoryService {

  private final CategoryRepository categoryRepository;

  public List<CategoryEntity> readCategoryAllList(Pageable pageable) {
    return categoryRepository.findByOrderByCategoryNameAsc(pageable);
  }

  public CategoryEntity findCategoryNumber(Integer categoryNumber) {
    return categoryRepository.findByCategoryNumber(categoryNumber);
  }

}
