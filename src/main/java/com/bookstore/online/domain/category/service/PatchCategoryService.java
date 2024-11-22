package com.bookstore.online.domain.category.service;

import com.bookstore.online.domain.category.entity.CategoryEntity;
import com.bookstore.online.domain.category.entity.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatchCategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryEntity findCategoryNumber(Integer categoryNumber) {
    return categoryRepository.findByCategoryNumber(categoryNumber);
  }

  public CategoryEntity updateCategory(CategoryEntity categoryEntity) {
    return categoryRepository.save(categoryEntity);
  }
}
