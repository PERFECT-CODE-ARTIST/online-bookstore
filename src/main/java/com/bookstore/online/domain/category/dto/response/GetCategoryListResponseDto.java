package com.bookstore.online.domain.category.dto.response;

import com.bookstore.online.domain.category.entity.CategoryEntity;
import com.bookstore.online.global.dto.ResponseCode;
import com.bookstore.online.global.dto.ResponseDto;
import com.bookstore.online.global.dto.ResponseMessage;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetCategoryListResponseDto extends ResponseDto {

  private List<CategoryEntity> categoryList = new ArrayList<>();

  public GetCategoryListResponseDto(List<CategoryEntity> categoryEntityList) {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    this.categoryList = getList(categoryEntityList);
  }

  public static ResponseEntity<GetCategoryListResponseDto> success(
      List<CategoryEntity> categoryEntities) {
    GetCategoryListResponseDto responseBody = new GetCategoryListResponseDto(categoryEntities);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

  //Category Entity -> Category Entity List
  public List<CategoryEntity> getList(List<CategoryEntity> categoryEntities) {
    List<CategoryEntity> categoryEntityList = new ArrayList<>();
    for (CategoryEntity categoryEntity : categoryEntities) {
      categoryEntityList.add(categoryEntity);
    }
    return categoryEntityList;
  }
}
