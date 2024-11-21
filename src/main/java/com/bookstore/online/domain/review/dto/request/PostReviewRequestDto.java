package com.bookstore.online.domain.review.dto.request;

// 리뷰 작성 요청을 위한 dto

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostReviewRequestDto {

  @NotNull
  private Integer bookNumber;

  @NotBlank
  private String rating;

  @NotBlank
  private String comment;

}
