package com.bookstore.online.domain.review.dto.request;

// 리뷰 수정 데이터를 전달하기 위한 객체

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchReviewRequestDto {

  @NotBlank
  private String rating;
  @NotBlank
  private String comment;

}
