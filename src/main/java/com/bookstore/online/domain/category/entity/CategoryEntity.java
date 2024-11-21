package com.bookstore.online.domain.category.entity;

import com.bookstore.online.domain.category.dto.request.PatchEditCategoryRequestDto;
import com.bookstore.online.domain.category.dto.request.PostCreateCategoryRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "categories")
@Table(name = "categories")
public class CategoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer categoryNumber;
  private String categoryName;

  public CategoryEntity(PostCreateCategoryRequestDto dto) {
    this.categoryName = dto.getCategoryName();
  }

  public void patchCategory(PatchEditCategoryRequestDto dto) {
    this.categoryName = dto.getCategoryName();
  }
}
