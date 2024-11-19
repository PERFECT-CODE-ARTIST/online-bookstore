package com.bookstore.online.domain.category.service;

import com.bookstore.online.domain.category.entity.CategoryEntity;
import com.bookstore.online.domain.category.entity.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCategoryService {
  private final CategoryRepository categoryRepository;

  public CategoryEntity findCategoryNumber(Integer categoryNanumber){
    return categoryRepository.findByCategoryNumber(categoryNanumber);
  }

  public void deleteCategory (Integer categoryNanumber) {
    categoryRepository.deleteById(categoryNanumber);
  }

}
