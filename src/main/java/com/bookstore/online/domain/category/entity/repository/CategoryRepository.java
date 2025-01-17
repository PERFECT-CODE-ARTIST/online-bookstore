package com.bookstore.online.domain.category.entity.repository;

import com.bookstore.online.domain.category.entity.CategoryEntity;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

  List<CategoryEntity> findByOrderByCategoryNameAsc(Pageable pageable);

  CategoryEntity findByCategoryNumber(Integer categoryNumber);
}
